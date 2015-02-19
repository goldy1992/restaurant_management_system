/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Response;

import Client.WaiterClient;

import Item.Tab;
import Message.Request.Request;
import Message.Request.TabRequest;
import Server.MyServer;

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
    public TabResponse(Request request)
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
