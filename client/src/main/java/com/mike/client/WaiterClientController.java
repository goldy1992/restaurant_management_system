package com.mike.client;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import com.mike.client.SelectTableMenu.SelectTableController;
import com.mike.message.Table.TableStatus;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.TableStatusResponse;

@MessageEndpoint
public class WaiterClientController {
	
	@Autowired
	private WaiterClient waiterClient;

	@Autowired
	private SelectTableController selectTableController;
	
    @Autowired
    public MessageSender messageSender;
	
	public void setWaiterClient(WaiterClient waiterClient) { this.waiterClient = waiterClient; }
	public void setSelectTableController(SelectTableController selectTableController) { this.selectTableController = selectTableController; }
	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	
	public void init() {
		messageSender.registerClient(ClientType.WAITER);
	}
	
    @ServiceActivator(inputChannel="registerClientResponseChannel")
    public void registerClientResponse(RegisterClientResponse registerClientResponse)
    {
        System.out.println("parse register client response");

	            
        if (!registerClientResponse.hasPermission()) {
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
    	
    	if (!selectTableController.isInitialised()) {
    		ArrayList<TableStatus> ts = new ArrayList<>();
    		for(Integer i : tableStatusResponse.getTableStatuses().keySet()) {
    			ts.add(tableStatusResponse.getTableStatuses().get(i));
    		}
    		selectTableController.init(ts);
    	}
    }

}
