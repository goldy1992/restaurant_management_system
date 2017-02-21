/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;


import org.springframework.integration.annotation.Gateway;


/**
 *
 * @author Mike
 */

public interface SendGateway {

    @Gateway(requestChannel="messageResponseChannel")
    public void send(Object message);
    
}
