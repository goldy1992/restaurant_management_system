package com.mike.client.frontend.MainMenu.voidItem;

import com.mike.client.backend.MessageSender;
import com.mike.client.frontend.Pair;
import com.mike.item.Item;
import com.mike.item.Tab;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Mike on 24/02/2017.
 */
public class VoidItemController  implements ActionListener{

    final static Logger logger = Logger.getLogger(VoidItemController.class);

    @Autowired
    private MessageSender messageSender;

    private VoidItemModel model;
    private VoidItemView view;

    public VoidItemController(){}

    public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }

    public Pair<Tab, Tab> startDialog(Dialog parent, boolean modal, Pair<Tab, Tab> tabPair) {
        model = new VoidItemModel(tabPair);
        view = new VoidItemView(parent, modal, model.getOldTab(), model.getNewTab());

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
        try {
            // TODO: change legacy connection code to perform a db query to the server
            Connection con;
            //init the connection to the database
            con = DriverManager.getConnection(
                    "jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3",
                    "mbbx9mg3",
                    "Fincherz+2013");

            // query: UPDATE `3YP_ITEMS` SET `QUANTITY` = `QUANTITY` - 1 WHERE ID = 27
            PreparedStatement numberOfButtonsQuery = null;
            String query = "UPDATE `3YP_ITEMS` "
                    + "SET `QUANTITY` = `QUANTITY` + \"" + quantity + "\" "
                    + "WHERE `3YP_ITEMS`.`ID` = \""
                    + item.getID() + "\"";

            logger.info("Query: " + query);

            numberOfButtonsQuery = con.prepareStatement(query);
            numberOfButtonsQuery.executeUpdate();

            con.close();
        }
        catch (SQLException ex) {
            logger.error(ex.getStackTrace().toString());
        }
    } // addToStock

    private boolean removeItems(ArrayList<Pair<Pair<JCheckBox, Item>,
            Pair<Component, Component>>> cBoxes, Tab tab) {
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
