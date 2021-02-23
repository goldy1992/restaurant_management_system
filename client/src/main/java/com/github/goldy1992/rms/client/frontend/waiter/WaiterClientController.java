package com.github.goldy1992.rms.client.frontend.waiter;

import com.github.goldy1992.rms.client.backend.MessageSender;
import com.github.goldy1992.rms.client.frontend.SelectTableMenu.SelectTableController;
import com.github.goldy1992.rms.message.Request.RegisterClientRequest.ClientType;
import com.github.goldy1992.rms.message.Response.RegisterClientResponse;
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
        getSelectTableController().init();
	}

	public SelectTableController getSelectTableController() { return selectTableController; }
}
