package com.mike.client;

import com.mike.client.SelectTableMenu.SelectTableController;
import com.mike.message.EventNotification.TableStatusEvtNfn;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Request.TableStatusRequest;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;

import java.util.*;

import com.mike.message.Table;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

/**
 *
 * @author Goldy
 */
@Component
public class WaiterClient extends UserClient
{
    public SelectTableController selectTable;  
    
    public WaiterClient() {
        super(ClientType.WAITER);
    }
    public WaiterClient(ClientType  type)
    {
        super(type);
    } // constructor
	private Map<Integer,Table> tableMap;

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
    	GenericXmlApplicationContext context = setupContext();
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
    

     

    @Override
    protected void parseTableStatusEvtNfn(TableStatusEvtNfn event) 
    {
        selectTable.setTableStatus(event.getTableNumber(), event.getTableStatus());      
    }
    
    public boolean sendTableStatusRequest(ArrayList<Integer> tables)
    {
        TableStatusRequest request = new TableStatusRequest(tables);
        messageSender.send(request);
        return true;
    }
    
    @Override
    @ServiceActivator(inputChannel="registerClientResponseChannel")
    public void registerClientResponse(RegisterClientResponse registerClientResponse)
    {
        System.out.println("parse register client response");

            
        if (!registerClientResponse.hasPermission())
        {
           // debugGUI.addText("A client already exists!");
            System.exit(0);                               
        } // if
        else { 
        	if (selectTable == null) {
        		sendTableStatusRequest(new ArrayList<>());
        	}
        	System.out.println("Client successfully registered as: " + registerClientResponse);        
        }
    } // regClientResp
    
    @ServiceActivator(inputChannel="tableStatusResponseChannel")
    public void tableStatusResponse(TableStatusResponse tableStatusResponse)
    {
		if (this.tableMap == null) {
			tableMap = new HashMap<>();
		}
		for (Integer t : tableStatusResponse.getTableStatuses().keySet()) {
			if (!tableMap.containsKey(t)) {
				Table table = new Table(t);
				tableMap.put(t, table);
			}
			tableMap.get(t).setTableStatus(tableStatusResponse.getTableStatuses().get(t));
		}
    }

    public static GenericXmlApplicationContext setupContext() {
		final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

		System.out.print("Detect open server socket...");
		int availableServerSocket = SocketUtils.findAvailableTcpPort();

		final Map<String, Object> sockets = new HashMap<String, Object>();
		sockets.put("availableServerSocket", availableServerSocket);

		final MapPropertySource propertySource = new MapPropertySource("sockets", sockets);

		context.getEnvironment().getPropertySources().addLast(propertySource);

		System.out.println("using port " + context.getEnvironment().getProperty("availableServerSocket"));

		context.load("/META-INF/client-context.xml");
		context.registerShutdownHook();
		context.refresh();

		return context;
	}
    
    
    
} // MyClientSocketClass
