package com.mike.client.frontend.waiter;

import com.mike.client.frontend.SelectTableMenu.SelectTableController;
import com.mike.client.backend.MessageSender;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Response.RegisterClientResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class WaiterClientController {

	@Autowired
	private SelectTableController selectTableController;
	
    @Autowired
    public MessageSender messageSender;

	public void setSelectTableController(SelectTableController selectTableController) { this.selectTableController = selectTableController; }
	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	
	public void init() {
		RegisterClientResponse registerClientResponse = messageSender.registerClient(ClientType.WAITER);
		
        if (!registerClientResponse.hasPermission()) {
        	System.exit(0);                               
    	} // if
        selectTableController.init();
	}
	}
