/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import com.mike.message.Message;
import com.mike.message.Request.RegisterClientRequest;

import java.util.Map;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.Headers;

/**
 *
 * @author Mike
 */
@MessageEndpoint
public class MessageFilter {
    
    @Filter(inputChannel = "inboundAdapterToFilterChannel", outputChannel = "filterToMessageTypeRouterChannel")
    public boolean accept(Object message, @Headers Map<String, Object> headerMap){
        System.out.println("message received filter\nFilter headers: " + headerMap);
        if (message instanceof Message) {
        	System.out.println("returning true");
        	return true; 
        } 
        
    	return false;
        
    }
   
}
