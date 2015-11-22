/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client;


import com.mike.message.Message;
import org.springframework.integration.annotation.Gateway;


/**
 *
 * @author Mike
 */
public interface MessageSender {

    @Gateway(requestChannel="inputChannel")
    public void send(Object message);
    
}
