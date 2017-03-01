package com.mike.client.frontend.MainMenu.voidItemMenu.voidItem;

import com.mike.item.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Mike on 26/02/2017.
 */
public class VoidItemView {

    private final JCheckBox checkBox;
    private final Component wastageOption;
    private final Component amountOption;

    public VoidItemView(VoidItem parent, Item item) {

        checkBox = new JCheckBox(item.getQuantity() + " " + item.getName());
        checkBox.addActionListener(parent);

        if (item.isStockCount()) {
            wastageOption = new JCheckBox("Wasted");
            wastageOption.setEnabled(false);
            ((JCheckBox) wastageOption).addActionListener(parent);
        } else {
            wastageOption = new JLabel("Stock Count Off");
        }

        if (item.getQuantity() > 1) {
            amountOption = new JTextField("");
            JTextField amountOptionJTextField = (JTextField)amountOption;
            amountOptionJTextField.getDocument().addDocumentListener(parent);
            amountOption.setEnabled(false);
        } else {
            amountOption = new JLabel("Only 1 Item");
        }
    } // constructor

    public void enableSubOptions(boolean option) {
        wastageOption.setEnabled(option);
        amountOption.setEnabled(option);
    }

    public JCheckBox getCheckBox() { return checkBox; }
    public Component getWastageOption() { return wastageOption; }
    public Component getAmountOption() { return amountOption; }

} // class
