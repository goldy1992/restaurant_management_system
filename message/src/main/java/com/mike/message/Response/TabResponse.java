/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.Response;


import com.mike.item.Tab;
import com.mike.message.Message;
import com.mike.message.Request.TabRequest;
import org.springframework.messaging.MessageHeaders;

/**
 *
 * @author mbbx9mg3
 */
public class TabResponse extends Response
{
    private Tab currentTab;
    
    /**
     *
     * @param request
     */
    public TabResponse(TabRequest request)
    {
        super(request);

    } // contructor
    
    public void setTab(Tab tab)
    {
        this.currentTab= tab;
    }
    
    public Tab getTab()
    {
        return currentTab;
    }



    
} // class
