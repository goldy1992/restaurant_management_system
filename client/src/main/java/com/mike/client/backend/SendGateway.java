/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.backend;


import org.springframework.integration.annotation.Gateway;


/**
 *
 * @author Mike
 */

public interface SendGateway {

    @Gateway(requestChannel="outputChannel")
    public void send(Object message);
    
}
