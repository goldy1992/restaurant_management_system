package com.github.goldy1992.rms.message.EventNotification;

import com.github.goldy1992.rms.message.Message;

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
