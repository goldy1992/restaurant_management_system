package Client;

import Client.SelectTableMenu.SelectTable;
import Message.EventNotification.TableStatusEvtNfn;
import Message.Message;
import Message.Request.RegisterClientRequest;
import Message.Request.*;
import Message.Request.RegisterClientRequest.ClientType;
import static Message.Request.RegisterClientRequest.ClientType.WAITER;
import static Message.Request.Request.RequestType.TABLE_STATUS;
import Message.Request.TableStatusRequest;
import static Message.Request.TableStatusRequest.ALL;
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
    public int numberOfTables = -1;
    public SelectTable selectTable;  
    
    public WaiterClient(ClientType  type) throws IOException
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
            myClient = (WaiterClient)Client.makeClient(WAITER);

            ArrayList<Integer> tables = new ArrayList<>();
            // add null because there's no table zero
            tables.add(ALL);
            InetAddress thisAddress = myClient.address;
            InetAddress serverAddress = myClient.serverAddress; 
            
            TableStatusRequest request = new TableStatusRequest(
                thisAddress,
                serverAddress,
                TABLE_STATUS,
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
