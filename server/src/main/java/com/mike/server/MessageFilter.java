/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import java.util.Map;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.Headers;
import com.mike.message.Message;

/**
 * @author Mike
 */
@MessageEndpoint
public class MessageFilter {
    
    @Filter(inputChannel = "inboundAdapterToFilterChannel", outputChannel = "filterToMessageTypeRouterChannel")
    public boolean accept(Object message, @Headers Map<String, Object> headerMap){
        System.out.println("message received filter\nFilter headers: " + headerMap);
        if (validMessage(message)) {
        	System.out.println("returning true");
        	return true; 
        }  // if
    	return false;
    } // accept
    
    private boolean validMessage(Object message) {
    	return message instanceof Message;
    }
   
}
