/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.EventNotification;

import Message.Message;
import java.net.InetAddress;

/**
 *
 * @author Goldy
 */
public abstract class EventNotification extends Message 
{

    /**
     *
     * @param from
     * @param to
     * @param messageID
     */
    public EventNotification(InetAddress from, InetAddress to)
    {
        super(from, to);
    }
    
    /**
     *
     * @return the string to represent the object plus the information in the super 
     * classes
     */
    @Override
    public String toString()
    {
        return super.toString() + "TYPE: Event Notification\n";
    }
    
}
