/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client;

import com.mike.message.EventNotification.EventNotification;
import com.mike.message.Message;
import com.mike.message.Request.Request;
import com.mike.message.Response.Response;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

/**
 *
 * @author Mike
 */
@MessageEndpoint
public class MessageTypeRouter {
    
    @Router(inputChannel = "filterToMessageTypeRouterChannel")
    public String accept(Message message){
        System.out.println("reached message router");
        if (message instanceof Response) {
			System.out.println("received a message response");
        	return "processFinished";
        }
//		if (message instanceof EventNotification) {
//			System.out.println("received a message response");
//			return "messageRequestChannel";
//		}
        
        return "messageRequestChannel";}
}

