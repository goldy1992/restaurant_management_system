package com.github.goldy1992.rms.client.frontend.MainMenu.voidItemMenu.voidItem;

import com.github.goldy1992.rms.item.Item;

/**
 * Created by Mike on 26/02/2017.
 */
public class VoidItemModel {

    private Item item;
    private boolean isSelected;
    private Integer amountToVoid;
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

    public Integer getAmountToVoid() { return amountToVoid; }
    public void setAmountToVoid(Integer amountToVoid) { this.amountToVoid = amountToVoid; }

    public boolean isWasted() { return isWasted; }

    public void setWasted(boolean wasted) {
        isWasted = wasted;
    }
}
