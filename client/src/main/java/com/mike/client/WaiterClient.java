package com.mike.client;

import com.mike.client.SelectTableMenu.SelectTableController;
import com.mike.message.EventNotification.TableStatusEvtNfn;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import static com.mike.message.Request.RegisterClientRequest.ClientType.WAITER;
import com.mike.message.Request.TableStatusRequest;
import static com.mike.message.Request.TableStatusRequest.ALL;
import com.mike.message.Response.NumOfTablesResponse;
import com.mike.message.Response.Response;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Goldy
 */
@Component
public class WaiterClient extends UserClient
{
    public int numberOfTables = -1;
    public SelectTableController selectTable;  
    
    public WaiterClient() {
        super(ClientType.WAITER);
    }
    public WaiterClient(ClientType  type)
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
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/client-context.xml");
        WaiterClient waiterClient = (WaiterClient)context.getBean("waiterClient");
        //MessageSender messageSender = (MessageSender)context.getBean("messageSender");
        waiterClient.registerClient();
        //WaiterClient client = (WaiterClient)Client.makeClient(WAITER);
   /*     ArrayList<Integer> tables = new ArrayList<>();
        tables.add(ALL); 
        client.sendTableStatusRequest(tables);
        
        synchronized(client.lock)
        {
            while(client.tableStatuses == null)
            {
                try { client.lock.wait(); }
                catch (InterruptedException e) 
                { // treat interrupt as exit request
                    break;
                }
            } // while
        
        } // synchronized
        //client.debugGUI.addText("post while");
        
        client.selectTable = new SelectTableController(
            client.tableStatuses, client);
       

        System.out.println("end of waiter client");
           */
    } // main
    
    @Override
    public void parseTabResponse(TabResponse resp)
    {
        //debugGUI.addText("Executing TabResponse's onreceiving");
        synchronized(selectTable)
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
    
    public boolean sendTableStatusRequest(ArrayList<Integer> tables)
    {
        TableStatusRequest request = new TableStatusRequest(
            address,
            serverAddress,
            tables);            
      //  return writeMessage(request);
        return true;
    }
    
} // MyClientSocketClass
