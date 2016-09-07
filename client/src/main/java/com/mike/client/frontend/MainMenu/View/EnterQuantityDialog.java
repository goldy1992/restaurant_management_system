/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.frontend.MainMenu.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author mbbx9mg3
 */
public class EnterQuantityDialog extends javax.swing.JDialog {

    
    private int tabNumber = 0;
    /**
     * Creates new form EnterQuantityDialog
     * @param parent
     * @param modal
     */
    public EnterQuantityDialog(Dialog parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();

    }
    
    private JPanel createKeypadPanel()
    {       
        JPanel newKeypadPanel = new JPanel();
        newKeypadPanel.setLayout(new GridLayout(4,3));
        final EnterQuantityDialog jd = this;
        final JButton cashPay = new JButton("Enter");
            cashPay.addActionListener(new ActionListener() 
            {        
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                   tabNumber = Integer.parseInt(output.getText());                   
                   jd.dispose();
                } // actionPerformed
            });
        
        // makes the first 9 buttons
        for (int i= 1; i <= 10; i++)
        {
             final int x; if (i == 10)  x = 0; else x = i; // declared final so can be used in the actionlistener method
            final JButton number = new JButton(x + "");
            number.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    output.setText(output.getText() + x);
                    cashPay.setEnabled(true);
                } // act performed
            });
            newKeypadPanel.add(number);
        } // for
        /* Make the clear button */
        final JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                    output.setText("");
                    cashPay.setEnabled(false);
            }
        });
        newKeypadPanel.add(clear);    
        

            newKeypadPanel.add(cashPay);
            cashPay.setEnabled(false);
         

        return newKeypadPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        output = new javax.swing.JTextField();
        keypadPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWeights = new double[] {1.0};
        layout.rowWeights = new double[] {0.2, 0.8};
        getContentPane().setLayout(layout);

        output.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(output, gridBagConstraints);

        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWeights = new double[] {1.0};
        jPanel1Layout.rowWeights = new double[] {1.0};
        keypadPanel.setLayout(jPanel1Layout);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;

        keypadPanel.add(createKeypadPanel(), gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(keypadPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel keypadPanel;
    private javax.swing.JTextField output;
    // End of variables declaration//GEN-END:variables

    public int getValue()
    {
        return tabNumber;
    } // getValue
    
    public static void main(String[] args)
    {
    JDialog j = new JDialog();
    j.setVisible(true);
    EnterQuantityDialog x = new EnterQuantityDialog(j, true);
    x.setVisible(true);
    
   
    } // main
} // class