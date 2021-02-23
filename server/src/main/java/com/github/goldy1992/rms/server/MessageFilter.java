/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goldy1992.rms.server;

import com.github.goldy1992.rms.message.Message;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;

/**
 * @author Mike
 */
@MessageEndpoint
public class MessageFilter {

    final static Logger logger = Logger.getLogger(MessageFilter.class);
    @Filter(inputChannel = "inboundAdapterToFilterChannel", outputChannel = "filterToMessageTypeRouterChannel")
    public boolean accept(Object message, @Headers Map<String, Object> headerMap){
        logger.debug("message received class type " + message.getClass());
        if (validMessage(message)) {
        	logger.debug("returning true");
        	return true; 
        }  // if
    	return false;
    } // accept
    
    private boolean validMessage(Object message) {
    	return message instanceof Message;
    }
   
}
