/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.EventNotification;

import com.mike.item.Tab;
import com.mike.message.Message;
import java.net.InetAddress;
import org.springframework.messaging.MessageHeaders;

/**
 *
 * @author mbbx9mg3
 */
public class TabUpdateNfn extends EventNotification {
   

    private final Tab tab;
    
    /**
     *
     * @param from
     * @param to
     * @param t the tab
     */
    public TabUpdateNfn(InetAddress from, 
                                InetAddress to, 
                                Tab t)
    {
        super(from, to);
        this.tab = t;
    }
    
    /**
     *
     * @return
     */
    public Tab getTab()
    {
        return tab;
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
