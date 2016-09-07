package com.mike.client.backend;

import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.Response;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;
import com.mike.message.Response.databaseResponse.QueryResponse;
import com.mike.message.Response.databaseResponse.UpdateResponse;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

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
	        } else if (response instanceof UpdateResponse) {
				return "dbUpdateResponseChannel";
			}
	        return "";}
}
