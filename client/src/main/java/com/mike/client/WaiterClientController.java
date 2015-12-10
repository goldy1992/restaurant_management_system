package com.mike.client;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.TableStatusResponse;

public class WaiterClientController {
	
	@Autowired
	private WaiterClient waiterClient;
	
    @Autowired
    public MessageSender messageSender;
	
	public void setWaiterClient(WaiterClient waiterClient) { this.waiterClient = waiterClient; }
	
	public void init() {
		messageSender.registerClient(ClientType.WAITER);
	}
	
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
    		if (waiterClient.selectTable == null) {
        		this.messageSender.sendTableStatusRequest(new ArrayList<>());
        	}
        	System.out.println("Client successfully registered as: " + registerClientResponse);        
        }
    } // regClientResp
	    
    @ServiceActivator(inputChannel="tableStatusResponseChannel")
    public void tableStatusResponse(TableStatusResponse tableStatusResponse)
    {
    	waiterClient.setTableStatuses(tableStatusResponse.getTableStatuses());
    }

}
