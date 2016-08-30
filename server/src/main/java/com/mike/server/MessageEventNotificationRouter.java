/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import com.mike.message.EventNotification.EventNotification;
import com.mike.message.EventNotification.TableStatusEvtNfn;
import com.mike.message.Message;
import com.mike.message.Request.RegisterClientRequest;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

/**
 *
 * @author Mike
 */
@MessageEndpoint
public class MessageEventNotificationRouter {
    
    @Router(inputChannel = "messageEventNotificationChannel")
    public String accept(EventNotification eventNotification){
        System.out.println("reached event notification router");
        if (eventNotification instanceof TableStatusEvtNfn) {
            return "messagetableStatusEventNotificationChannel";
        }
        return null;
    }
}

