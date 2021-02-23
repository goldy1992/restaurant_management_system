/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goldy1992.rms.client.backend;

import com.github.goldy1992.rms.message.EventNotification.EventNotification;
import com.github.goldy1992.rms.message.Response.Response;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

/**
 *
 * @author Mike
 */
@MessageEndpoint
public class MessageTypeRouter {

    final static Logger logger = Logger.getLogger(MessageTypeRouter.class);
    
    @Router(inputChannel = "filterToMessageTypeRouterChannel")
    public String accept(Object message){
        logger.info("reached message router");
        if (message instanceof Response) {
			logger.info("received a message response");
        	return "typeRouterToResponseChannel";
        } else if (message instanceof EventNotification) {
			logger.info("received a message event notification");
			return "typeRouterToEventNotificationChannel";
		}
        
        return "messageRequestChannel";}
}

