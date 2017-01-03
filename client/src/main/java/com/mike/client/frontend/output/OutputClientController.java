package com.mike.client.frontend.output;

import com.mike.client.backend.MessageSender;
import com.mike.message.EventNotification.NewItemNfn;
import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Response.RegisterClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * Created by Mike on 03/01/2017.
 */
@MessageEndpoint
public class OutputClientController {

    @Autowired
    public MessageSender messageSender;

    private OutputGUI view;

    public OutputClientController() {    }

    public void init(RegisterClientRequest.ClientType clientType){
        RegisterClientResponse registerClientResponse = messageSender.registerClient(clientType);

        if (!registerClientResponse.hasPermission()) {
            System.exit(0);
        } // if
        this.view = new OutputGUI();
        view.setVisible(true);
    }

    @ServiceActivator(inputChannel="NewItemNotificationChannel")
    public void setTableStatus(NewItemNfn newItemNfn) {
        view.addMessage(newItemNfn);
    }
}
