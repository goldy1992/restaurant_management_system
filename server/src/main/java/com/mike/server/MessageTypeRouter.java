/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import com.mike.message.EventNotification.EventNotification;
import com.mike.message.Message;
import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Request.Request;

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
        if (message instanceof Request) {
        	return "messageRequestChannel";
        } else if (message instanceof EventNotification) {
            return "messageEventNotificationChannel";
        }
        
        return "messageRequestChannel";}
}

