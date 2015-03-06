/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import Client.SelectTableMenu.SelectTable;
import Message.EventNotification.TableStatusEvtNfn;
import Message.Message;
import Message.Request.RegisterClientRequest;
import Message.Request.*;
import Message.Request.TableStatusRequest;
import Message.Response.NumOfTablesResponse;
import Message.Response.Response;
import Message.Response.TabResponse;
import Message.Response.TableStatusResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */
public class WaiterClient extends UserClient
{
   
    /**
     * An object used to ensure tasks are performed asynchronously. 
     */

    public int numberOfTables = -1;
    public SelectTable selectTable;
    
    
    public WaiterClient(RegisterClientRequest.ClientType  type) throws IOException
    {
        super(type);
    } // constructor
      
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
    
    @Override
    public void parseTabResponse(TabResponse resp)
    {
        debugGUI.addText("Executing TabResponse's onreceiving");
        synchronized(selectTable.tabLock)
        {
            selectTable.setTab(resp.getTab());
            selectTable.setTabReceived(true);      
            selectTable.tabLock.notifyAll();
        } // synchronized        
    }
    
    private void parseNumOfTablesResponse(NumOfTablesResponse resp)
    {       
        synchronized(lock)
        {
            numberOfTables = resp.getNumOfTables();
            lock.notifyAll();
        } // sync
    } // parseNumOfTablesResponse
     
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
    protected void parseTableStatusEvtNfn(TableStatusEvtNfn event) 
    {
        selectTable.setTableStatus(event.getTableNumber(), event.getTableStatus());      
    }
   

} // MyClientSocketClass
