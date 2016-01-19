package com.mike.client;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

import com.mike.message.EventNotification.EventNotification;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.Response;
import com.mike.message.Response.TableStatusResponse;

@MessageEndpoint
public class MessageResponseRouter {
	
	  @Router(inputChannel = "typeRouterToResponseChannel")
	  public String accept(Response response){
	        if (response instanceof RegisterClientResponse) {
	        	return "registerClientResponseChannel";
	        } else if (response instanceof TableStatusResponse) {
	        	return "tableStatusResponseChannel";
	        }
	        
	        return "";}

}
