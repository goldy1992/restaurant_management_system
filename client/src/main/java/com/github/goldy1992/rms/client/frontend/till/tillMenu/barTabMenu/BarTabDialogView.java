/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goldy1992.rms.client.frontend.till.tillMenu.barTabMenu;

import com.github.goldy1992.rms.message.Table;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mbbx9mg3
 */
public class BarTabDialogView extends javax.swing.JDialog {

    final static Logger logger = Logger.getLogger(BarTabDialogView.class);

    private List<TabJButton> tabJButtons;

    public BarTabDialogView(Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void init(Map<Integer, Table.TableStatus> statuses, BarTabMenuController barTabMenuController,
                     BarTabMenuModel.Functionality functionality) {
        this.getContentPane().removeAll();
        if (functionality == BarTabMenuModel.Functionality.ADD_TO_TAB) {
            JButton newTabButton = new JButton("New Tab");
            this.getContentPane().add(newTabButton);
            newTabButton.addActionListener(barTabMenuController);
        }
        setButtons(statuses, barTabMenuController);
    }

    public synchronized void setButtons(Map<Integer, Table.TableStatus> statuses, BarTabMenuController barTabMenuController) {
        // SET UP NEW TAB BUTTON
        tabJButtons = new ArrayList<>();
        logger.info("set buttons: " + statuses.size() + " buttons");
        for (Integer i : statuses.keySet()) {
            TabJButton jButton = new TabJButton(i);
            jButton.addActionListener(barTabMenuController);
            tabJButtons.add(jButton);
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

    public List<TabJButton> getTabJButtons() { return tabJButtons; }

    public void setTabJButtons(List<TabJButton> tabJButtons) { this.tabJButtons = tabJButtons;    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
