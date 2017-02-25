package com.mike.client.frontend.MainMenu.voidItem;

import com.mike.client.frontend.Pair;
import com.mike.item.Tab;

/**
 * Created by Mike on 24/02/2017.
 */
public class VoidItemModel {

    private final Tab oldTab;
    private final Tab newTab;

    public VoidItemModel(Pair<Tab, Tab> tab)  {
        this.oldTab = tab.getFirst();
        this.newTab = tab.getSecond();
    }

    public Tab getOldTab() { return oldTab; }
    public Tab getNewTab() { return newTab;    }
} // class
