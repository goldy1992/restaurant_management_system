/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.Request;

import com.mike.message.Message;

import java.io.Serializable;
import java.net.InetAddress;
import org.springframework.messaging.MessageHeaders;
/**
 *
 * @author mbbx9mg3
 */
public class RegisterClientRequest extends Request implements Serializable
{

    public static enum ClientType
    {
        /**
         *  Request registers the bar client.
         */
        BAR,
        
        /**
         *  Request registers the kitchen client.
         */
        KITCHEN,
        
        /**
         *  Request registers the waiter client.
         */
        WAITER,
        
        /**
         *  Request registers the till client.
         */
        TILL
    } // register
    
    private ClientType clientType;
    
    /**
     *
     * @param from
     * @param to
     * @param clientType
     */
    public RegisterClientRequest(InetAddress from, 
                                InetAddress to, 
                                ClientType clientType)
    {
        this.clientType = clientType;
    } // constructor
    
    @Override
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: " + clientType;
        x+= "\n";
        
        return x;
    } // to String
    
    public ClientType getClientType()
    {
        return clientType;
    }
    
}
