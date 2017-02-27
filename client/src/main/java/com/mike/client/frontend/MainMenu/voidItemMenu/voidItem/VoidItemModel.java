package com.mike.client.frontend.MainMenu.voidItemMenu.voidItem;

import com.mike.item.Item;

/**
 * Created by Mike on 26/02/2017.
 */
public class VoidItemModel {

    private Item item;
    private boolean isSelected;
    private int amountToVoid;
    private boolean isWasted;

    public VoidItemModel(Item item) {
        this.setItem(item);
        this.setSelected(false);
        this.setAmountToVoid(0);
        this.setWasted(false);
    }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item;    }

    public boolean isSelected() { return isSelected; }

    public void setSelected(boolean selected) { isSelected = selected; }
    public int getAmountToVoid() { return amountToVoid; }

    public void setAmountToVoid(int amountToVoid) { this.amountToVoid = amountToVoid; }

    public boolean isWasted() { return isWasted; }

    public void setWasted(boolean wasted) {
        isWasted = wasted;
    }
}
