package com.mike.client.frontend.till;

import com.mike.item.Tab;

/**
 * Created by Mike on 20/02/2017.
 */
public class TillModel {

    private Tab previousTab;
    private Tab currentTab;

    public TillModel() {
        setCurrentTab(new Tab());
        setPreviousTab(new Tab());
    }

    public Tab getPreviousTab() { return previousTab; }
    public void setPreviousTab(Tab previousTab) { this.previousTab = previousTab;    }

    public Tab getCurrentTab() { return currentTab; }
    public void setCurrentTab(Tab currentTab) {  this.currentTab = currentTab; }

} // class
