package com.mike.client.frontend.MainMenu.voidItemMenu.voidItem;

import com.mike.item.Item;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mike on 25/02/2017.
 */
public class VoidItem implements ActionListener {

    private VoidItemModel voidItemModel;
    private VoidItemView voidItemView;

    public VoidItem(Item item) {
        voidItemModel = new VoidItemModel(item);
        voidItemView = new VoidItemView(this, item);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getVoidItemView().getCheckBox()) {
            if (getVoidItemView().getCheckBox().isSelected()) {
                getVoidItemView().enableSubOptions(true);
            } else {
                getVoidItemView().enableSubOptions(false);
            } // else
        }
    }

    public VoidItemModel getVoidItemModel() { return voidItemModel; }
    public VoidItemView getVoidItemView() { return voidItemView; }

} // class
