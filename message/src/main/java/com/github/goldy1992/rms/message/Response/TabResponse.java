package com.github.goldy1992.rms.message.Response;

import com.github.goldy1992.rms.item.Tab;
import com.github.goldy1992.rms.message.Request.TabRequest;

/**
 *
 * @author mbbx9mg3
 */
public class TabResponse extends Response {

    private Tab currentTab;
    
    /**
     *
     * @param request
     */
    public TabResponse(TabRequest request) {
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
