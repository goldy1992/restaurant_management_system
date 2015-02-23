/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import Client.SelectTableMenu.SelectTable;
import Message.EventNotification.EventNotification;
import Message.EventNotification.TableStatusEvtNfn;
import Message.Table;
import Message.Message;
import Message.Request.NumOfTablesRequest;
import Message.Request.RegisterClientRequest;
import Message.Request.Request;
import Message.Request.TableStatusRequest;
import Message.Response.NumOfTablesResponse;
import Message.Response.Response;
import Message.Response.TabResponse;
import Message.Response.TableStatusResponse;
import Server.MyServer;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */
public class WaiterClient extends Client
{
   
    /**
     * An object used to ensure tasks are performed asynchronously. 
     */
    public final Object lock = new Object();
    public ArrayList<Table.TableStatus> tableStatuses = null; // temp variable
    public int numberOfTables = -1;
    public SelectTable selectTable;
    
    
    public WaiterClient(RegisterClientRequest.ClientType  type) throws IOException
    {
        super(type);
    } // constructor
     
    /**
     * @param resp
     */
   public void setTableStatuses(TableStatusResponse resp)
   {
       ArrayList<Table.TableStatus>  x = resp.getTableStatuses();
       TableStatusRequest req = (TableStatusRequest)resp.getRequest();
       
       for (int i = 0; i < req.getTableList().size(); i++)
           tableStatuses.set(req.getTableList().get(i),
                   x.get(i));
       
   }
   
    /**
     * @return The number of tables in use.
     */
   public int getNumTables()
   {
        return numberOfTables;
   }
   
    /**
     * A mutator method to set the number of tables.
     * @param numTables the number of the tables to be run
     */
    public void setNumTables(int numTables)
   {
        numberOfTables = numTables;   
   }
   
    
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        WaiterClient myClient = null;
        try
        {
            myClient = Client.makeClient(RegisterClientRequest.ClientType.WAITER);
            System.out.println("is MyCLient: " + (myClient.getClass() == WaiterClient.class));


            ArrayList<Integer> tables = new ArrayList<>();
            // add null because there's no table zero
            tables.add(-1);
                
            TableStatusRequest request = new TableStatusRequest(
                InetAddress.getByName(
                    myClient.client.getLocalAddress().getHostName()),
                InetAddress.getByName(
                    myClient.serverAddress.getHostName() ),
                Message.generateRequestID(),
                Request.RequestType.TABLE_STATUS,
                tables);
            myClient.getOutputStream().writeObject(request);  
        } // try
        catch (IOException e)
        {
            myClient.debugGUI.addText("Could not connect to server");
        } // catch
        //myClient.debugGUI.addText("pre while");
        
        
        synchronized(myClient.lock)
        {
            while(myClient.tableStatuses == null)
            {
                try { myClient.lock.wait(); }
                catch (InterruptedException e) 
                { // treat interrupt as exit request
                    break;
                }
            } // while
        
        } // synchronized
        myClient.debugGUI.addText("post while");
        
        myClient.selectTable = new SelectTable(
            myClient.tableStatuses, myClient.getOutputStream(), myClient);
        myClient.selectTable.setVisible(true);

        System.out.println("end of waiter client");
    } // main
    
    private void parseTabResponse(TabResponse resp)
    {
        debugGUI.addText("Executing TabResponse's onreceiving");
        synchronized(selectTable.tabLock)
        {
            selectTable.setTab(resp.getTab());
            selectTable.setTabReceived(true);      
            selectTable.tabLock.notifyAll();
        } // synchronized        
    }
    
    private void parseTableStatusResponse(TableStatusResponse resp)
    {
        System.out.println("table status response");
       System.out.println(resp.getTableStatuses());
       
       TableStatusRequest req = (TableStatusRequest)resp.getRequest();
       

       
        synchronized(lock)
        {
            if (req.getTableList().size() == 1 && req.getTableList().get(0) == -1)
                this.tableStatuses = resp.getTableStatuses();
            else
                setTableStatuses(resp);
            lock.notifyAll();
        }   
    }
    
    private void parseNumOfTablesResponse(NumOfTablesResponse resp)
    {

        
        synchronized(lock)
        {
        numberOfTables = resp.getNumOfTables();
            lock.notifyAll();
        }

    }
     
   @Override
   public void parseResponse(Response resp) throws IOException, ClassNotFoundException
   {
        System.out.println("response received");
        super.parseResponse(resp);

        if (resp instanceof TabResponse) 
            parseTabResponse((TabResponse)resp);
        if (resp instanceof TableStatusResponse) 
            parseTableStatusResponse((TableStatusResponse)resp);
   }
   
   @Override
   public void parseEventNotification(EventNotification evntNfn) throws IOException, ClassNotFoundException
   {
        super.parseEventNotification(evntNfn);
        if (evntNfn instanceof TableStatusEvtNfn)
        {
            TableStatusEvtNfn r = (TableStatusEvtNfn)evntNfn; 
            selectTable.setTableStatus(r.getTableNumber(), r.getTableStatus());                 
         } // inner if
       
   }
} // MyClientSocketClass
