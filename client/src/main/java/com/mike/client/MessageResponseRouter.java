package com.mike.client;

import org.springframework.integration.annotation.Router;

import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.Response;

public class MessageResponseRouter {
	
	  @Router(inputChannel = "typeRouterToResponseChannel")
	  public String accept(Response response){
	        if (response instanceof RegisterClientResponse) {
	        	return "typeRouterToResponseChannel";
	        }
//			if (message instanceof EventNotification) {
//				System.out.println("received a message response");
//				return "messageRequestChannel";
//			}
	        
	        return "";}

}
