/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.EventNotification;


import java.net.InetAddress;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 *
 * @author Goldy
 */
public abstract class EventNotification
{

    /**
     *
     * @param from
     * @param to
     * @param messageID
     */
    public EventNotification()
    {
       
    }
    
    /**
     *
     * @return the string to represent the object plus the information in the super 
     * classes
     */
    @Override
    public String toString()
    {
        return "TYPE: Event Notification\n";
    }


    
}
