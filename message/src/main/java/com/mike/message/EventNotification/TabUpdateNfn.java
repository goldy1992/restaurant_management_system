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
     * @param t the tab
     */
    public TabUpdateNfn(Tab t) {
        super();
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
}
