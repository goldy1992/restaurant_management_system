package com.github.goldy1992.rms.client.frontend.output;

import com.github.goldy1992.rms.client.backend.MessageSender;
import com.github.goldy1992.rms.message.EventNotification.NewItemNfn;
import com.github.goldy1992.rms.message.Request.RegisterClientRequest;
import com.github.goldy1992.rms.message.Response.RegisterClientResponse;
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
        this.view = new OutputGUI(clientType);
        view.setVisible(true);
    }

    @ServiceActivator(inputChannel="NewItemNotificationChannel")
    public void setTableStatus(NewItemNfn newItemNfn) {
        view.addMessage(newItemNfn);
    }
}
