package com.github.goldy1992.rms.client.frontend.MainMenu.voidItemMenu;

import com.github.goldy1992.rms.client.frontend.MainMenu.voidItemMenu.voidItem.VoidItem;
import com.github.goldy1992.rms.item.Item;
import com.github.goldy1992.rms.item.Tab;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 24/02/2017.
 */
public class VoidItemMenuModel {

    private final Tab oldTab;
    private final Tab newTab;

    private List<VoidItem> voidItems;

    public VoidItemMenuModel(Tab oldTab, Tab newTab)  {
        voidItems = new ArrayList<>();
        this.oldTab = oldTab;
        this.newTab = newTab;

        for (Item item : oldTab.getItems()) {
            getVoidItems().add(new VoidItem(item));
        }

        for (Item item : newTab.getItems()) {
            getVoidItems().add(new VoidItem(item));
        }
    }

    public Tab getOldTab() { return oldTab; }
    public Tab getNewTab() { return newTab;    }
    public List<VoidItem> getVoidItems() { return voidItems; }
} // class
