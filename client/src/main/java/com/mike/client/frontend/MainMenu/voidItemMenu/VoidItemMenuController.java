package com.mike.client.frontend.MainMenu.voidItemMenu;

import com.mike.client.backend.MessageSender;
import com.mike.client.frontend.Pair;
import com.mike.item.Item;
import com.mike.item.Tab;
import com.mike.message.Response.databaseResponse.QueryResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Mike on 24/02/2017.
 */
public class VoidItemMenuController implements ActionListener{

    final static Logger logger = Logger.getLogger(VoidItemMenuController.class);

    @Autowired
    private MessageSender messageSender;

    private VoidItemMenuModel model;
    private VoidItemMenuView view;

    public VoidItemMenuController(){}

    public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }

    public Pair<Tab, Tab> startDialog(Dialog parent, boolean modal, Tab oldTab, Tab newTab) {
        model = new VoidItemMenuModel(oldTab, newTab);
        view = new VoidItemMenuView(parent, modal, model.getOldTab(), model.getNewTab(), this, model.getVoidItems());

        view.startDialog();
        return new Pair<>(model.getOldTab(), model.getNewTab());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getSubmitButton()) {
            if (removeItems(view.getcBoxesOldTab(), model.getOldTab())
                    &&  removeItems(view.getcBoxesNewTab(), model.getNewTab()))
                view.setVisible(false);
        } else if (e.getSource() == view.getCancelButton()) {
            view.dispose();
        }
    }


    private void addToStock(Item item, int quantity) {
        String query = "UPDATE `3YP_ITEMS` "
                + "SET `QUANTITY` = `QUANTITY` + \"" + quantity + "\" "
                + "WHERE `3YP_ITEMS`.`ID` = \""
                + item.getID() + "\"";
        QueryResponse response = messageSender.sendDbQuery(query);
        logger.info(response);
    } // addToStock

    private boolean removeItems(ArrayList<Pair<Pair<JCheckBox, Item>, Pair<Component, Component>>> cBoxes, Tab tab) {
        CopyOnWriteArrayList<Item> temp = new  CopyOnWriteArrayList<>();
        temp.addAll(tab.getItems());

        // the first for check for errors
        for (Pair<Pair<JCheckBox, Item>, Pair<Component, Component>> jCB : cBoxes) {
            if (jCB.getFirst().getFirst().isSelected() && (jCB.getSecond().getSecond() instanceof JTextField)) {
                int amount = 0;
                try {
                    JTextField tf = (JTextField)jCB.getSecond().getSecond();
                    amount = Integer.parseInt(tf.getText());
                }
                catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Incorrect number format! Please check your formatting!");
                    return false;
                }

                if (amount >= jCB.getFirst().getSecond().getQuantity() || amount <= 0) {
                    JOptionPane.showMessageDialog(view, "Incorrect number format! Please check your formatting!");
                    return false;
                }
            } // if
        }  // for


        for (Pair<Pair<JCheckBox, Item>, Pair<Component, Component>> jCB : cBoxes) {
            if (jCB.getFirst().getFirst().isSelected()) {
                for (Item i : temp) {
                    if (jCB.getFirst().getSecond() == i) {
                        if (jCB.getFirst().getSecond().getQuantity() == 1) {
                            temp.remove(i);
                            if (i.stockCount) {
                                JCheckBox tf = (JCheckBox) jCB.getSecond().getFirst();
                                if (!tf.isSelected())
                                    addToStock(i, 1);
                            } // if
                        } // if
                        else {
                            JTextField tf = (JTextField) jCB.getSecond().getSecond();
                            int amount = Integer.parseInt(tf.getText());
                            if (amount == jCB.getFirst().getSecond().getQuantity()) {
                                temp.remove(i);
                            } else {
                                i.setQuantity(i.getQuantity() - amount);
                            }

                            if (i.stockCount) {
                                JCheckBox tf1 = (JCheckBox) jCB.getSecond().getFirst();
                                if (!tf1.isSelected()) {
                                    addToStock(i, amount);
                                }
                            } // if
                        } // else
                    } // if
                }
            } // if
        } // for

        tab.removeAll();
        for (Item i :temp ) {
            tab.addItem(i);
        }
        return true;
    }
} // class
