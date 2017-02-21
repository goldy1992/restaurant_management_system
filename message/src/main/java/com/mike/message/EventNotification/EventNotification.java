package com.mike.message.EventNotification;

import com.mike.message.Message;

/**
 *
 * @author Goldy
 */
public abstract class EventNotification extends Message {

    public EventNotification() { }
    
    /**
     *
     * @return the string to represent the object plus the information in the super 
     * classes
     */
    @Override
    public String toString() { return "TYPE: Event Notification\n"; }
} // class
