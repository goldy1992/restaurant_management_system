package com.github.goldy1992.rms.client.backend;

import com.github.goldy1992.rms.message.EventNotification.EventNotification;
import com.github.goldy1992.rms.message.EventNotification.NewItemNfn;
import com.github.goldy1992.rms.message.EventNotification.TableStatusEvtNfn;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

@MessageEndpoint
public class MessageEventNotificationRouter {
	 @Router(inputChannel = "typeRouterToEventNotificationChannel")
	  public String accept(EventNotification notification){
	        if (notification instanceof TableStatusEvtNfn) {
	        	return "tableStatusEvtNotificationChannel";
	        } else if (notification instanceof NewItemNfn) {
			 	return "NewItemNotificationChannel";
		 	}
	        return "";
	 }


}
