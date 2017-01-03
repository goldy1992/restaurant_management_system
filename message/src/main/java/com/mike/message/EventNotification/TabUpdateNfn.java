/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.EventNotification;

import com.mike.item.Tab;

/**
 *
 * @author mbbx9mg3
 */
public class TabUpdateNfn extends EventNotification {

    private final Tab updatedTab;
    private Tab newItems;
    
    /**
     *
     * @param updatedTab the new and the old tabbed merged
     * @param newItems the new items that were added to the tab
     */
    public TabUpdateNfn(Tab updatedTab, Tab newItems) {
        super();
        this.updatedTab = updatedTab;
        this.newItems = newItems;
    }
    
    /**
     *
     * @return
     */
    public Tab getUpdatedTab()
    {
        return updatedTab;
    }
    public Tab getNewItems() { return newItems;    }
    public void setNewItems(Tab newItems) { this.newItems = newItems; }
}
