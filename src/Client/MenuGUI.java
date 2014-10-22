/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextPane;

/**
 *
 * @author mbbx9mg3
 */
public class MenuGUI extends javax.swing.JDialog implements ActionListener
{
    private Menu menu;
    
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource() == menu.getSendOrderButton())
        {
            try 
            {
                menu.con.close();
            } catch (SQLException ex) 
            {
                Logger.getLogger(MenuGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();;
        }
    }  

    /**
     * Creates new form Menu
     */
    public MenuGUI(java.awt.Frame parent, boolean modal, Menu menu) 
    {
        super(parent, modal);
        this.menu = menu;
        try 
        {         
            menu.con = DriverManager.getConnection("jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3", "mbbx9mg3", "Fincherz+2013");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initComponents();

 
    } // constructor
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FormPanel = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        FormPanel.setPreferredSize(new java.awt.Dimension(400, 500));
        FormPanel.setLayout(new java.awt.GridBagLayout());

        jMenu1.setText("File");
        MenuBar.add(jMenu1);

        jMenu2.setText("Edit");
        MenuBar.add(jMenu2);

        setJMenuBar(MenuBar);
        MenuBar.setFocusable(false);
        MenuBar.requestFocus(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 2654, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
 
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MenuGUI dialog = new MenuGUI(new JFrame(), true, MyClient.selectTable.menu);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FormPanel;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    // End of variables declaration//GEN-END:variables
    


}