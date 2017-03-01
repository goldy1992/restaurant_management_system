package com.mike.client.frontend.MainMenu.voidItemMenu.voidItem;

import com.mike.item.Item;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by Mike on 25/02/2017.
 */
public class VoidItem implements ActionListener, DocumentListener {

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
                voidItemModel.setSelected(true);
                if (voidItemModel.getItem().getQuantity() == 1) {
                    getVoidItemModel().setAmountToVoid(1);
                }
            } else {
                getVoidItemView().enableSubOptions(false);
                if (voidItemModel.getItem().getQuantity() == 1) {
                    getVoidItemModel().setAmountToVoid(1);
                }
                voidItemModel.setSelected(false);
            } // else
        } // if getSource

        if (e.getSource() == getVoidItemView().getWastageOption() && e.getSource() instanceof JCheckBox) {
            JCheckBox wasteCheckBox = (JCheckBox) voidItemView.getWastageOption();
            voidItemModel.setWasted(wasteCheckBox.isSelected());
        }
    } // actionPerformed

    public VoidItemModel getVoidItemModel() { return voidItemModel; }
    public VoidItemView getVoidItemView() { return voidItemView; }


    @Override
    public void insertUpdate(DocumentEvent e) { parseDocumentEvent(e); }

    @Override
    public void removeUpdate(DocumentEvent e) { parseDocumentEvent(e);     }

    @Override
    public void changedUpdate(DocumentEvent e) { parseDocumentEvent(e); }

    private void parseDocumentEvent(DocumentEvent event) {
        JTextField amount = (JTextField) voidItemView.getAmountOption();
        String value = amount.getText();

        try {
            int intValue = Integer.parseInt(value);
            voidItemModel.setAmountToVoid(intValue);
        } catch (NumberFormatException ex) {
            voidItemModel.setAmountToVoid(null);
        }

    }
} // class
