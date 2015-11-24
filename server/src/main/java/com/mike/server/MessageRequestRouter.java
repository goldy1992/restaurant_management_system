/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import com.mike.message.Message;
import com.mike.message.Request.*;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

/**
 *
 * @author Mike
 */
@MessageEndpoint
public class MessageRequestRouter {
    
    @Router(inputChannel = "messageRequestChannel")
    public String accept(Request request){
        System.out.println("reached message request router");
        if (request instanceof RegisterClientRequest) {
			System.out.println("return registerclient request router");
        	return "messageRegisterClientRequestChannel";
        }
        return "messageRequestChannel";}
}

