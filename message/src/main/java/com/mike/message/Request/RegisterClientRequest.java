/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.Request;

import java.net.InetAddress;
/**
 *
 * @author mbbx9mg3
 */
public class RegisterClientRequest extends Request {

    public static enum ClientType {
        BAR,
        KITCHEN,
        WAITER,
        TILL
    } // register
    
    private ClientType clientType;
    
    /**
     *
     * @param clientType
     */
    public RegisterClientRequest(ClientType clientType) {
        this.clientType = clientType;
    } // constructor
    
    @Override
    public String toString() {
        String x = super.toString() + "SUBTYPE: " + clientType;
        x+= "\n";
        return x;
    } // to String
    
    public ClientType getClientType() { return clientType; }
} // class
