/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.frontend.till.tillMenu.barTabMenu;

import com.mike.client.frontend.Pair;
import com.mike.client.frontend.till.tillMenu.TillMenuView;
import com.mike.message.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * @author mbbx9mg3
 */
public class BarTabDialogView extends javax.swing.JDialog {

    private BarTabMenuController barTabMenuController;

    /**
     * Creates new form BarTabDialogView
     * @param parent
     * @param modal
     */
    public BarTabDialogView(BarTabMenuController barTabMenuController,
                            Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.barTabMenuController = barTabMenuController;

    }

    public void init(Map<Integer, Table.TableStatus> statuses, BarTabMenuModel.Functionality functionality) {
        this.getContentPane().removeAll();
        if (functionality == BarTabMenuModel.Functionality.ADD_TO_TAB) {
            JButton newTabButton = new JButton("New Tab");
            this.getContentPane().add(newTabButton);
            newTabButton.addActionListener(barTabMenuController);
        }
        setButtons(statuses);
    }

    public synchronized void setButtons(Map<Integer, Table.TableStatus> statuses) {
        // SET UP NEW TAB BUTTON
        System.out.println("set buttons: " + statuses.size() + " buttons");
        for (Integer i : statuses.keySet()) {
            TabJButton jButton = new TabJButton(i);
            jButton.addActionListener(barTabMenuController);
            this.getContentPane().add(jButton);
        }

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(barTabMenuController);
        this.getContentPane().add(cancelButton);
        this.pack();
        this.doLayout();
        this.revalidate();
        this.repaint();
    }

//    public void setState(Functionality f) {
//        this.func = f;
//        this.setButtons(currentStatuses);
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
       // @SuppressWarnings("unchecked")
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bar Tabs");
        getContentPane().setLayout(new java.awt.GridLayout(0, 5));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
