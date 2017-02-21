package com.mike.client.backend;

import com.mike.message.EventNotification.EventNotification;
import com.mike.message.EventNotification.NewItemNfn;
import com.mike.message.EventNotification.TableStatusEvtNfn;
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
