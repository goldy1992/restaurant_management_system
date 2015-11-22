/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.Request;

import com.mike.message.Message;
import java.net.InetAddress;
import org.springframework.messaging.MessageHeaders;

/**
 *
 * @author mbbx9mg3
 */
public class LeaveRequest extends Request
{

    /**
     *
     * @param from
     * @param to
     * @param messageID
     * @param type
     */
    public LeaveRequest(InetAddress from, 
                              InetAddress to)
    {
        super(from, to);
    } // constructor
    

    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Leave";
        x+= "\n";
        
        return x;
    }   

    @Override
    public Message getPayload() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MessageHeaders getHeaders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
