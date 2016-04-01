package com.mike.client;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

import com.mike.message.EventNotification.EventNotification;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.Response;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;
import com.mike.message.Response.databaseResponse.QueryResponse;

@MessageEndpoint
public class MessageResponseRouter {
	
	  @Router(inputChannel = "typeRouterToResponseChannel")
	  public String accept(Response response){
	        if (response instanceof RegisterClientResponse) {
	        	return "registerClientResponseChannel";
	        } else if (response instanceof TableStatusResponse) {
	        	return "tableStatusResponseChannel";
	        } else if (response instanceof TabResponse) {
	        	return "tabResponseChannel";
	        } else if (response instanceof QueryResponse) {
	        	return "dbQueryResponseChannel";
	        }
	        
	        
	        return "";}

}
