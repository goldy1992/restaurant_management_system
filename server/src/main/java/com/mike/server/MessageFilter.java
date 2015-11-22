/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import com.mike.message.Message;
import com.mike.message.Request.RegisterClientRequest;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;

/**
 *
 * @author Mike
 */
@MessageEndpoint
public class MessageFilter {
    
    @Filter(inputChannel = "gatewayToFilterChannel", outputChannel = "filterToMessageTypeRouterChannel")
    public boolean accept(Object message){
        System.out.println("message received filter");
        if (message instanceof Message) {
        	System.out.println("returning true");
        	return true; 
        } 
        
    	return false;
        
    }
}
