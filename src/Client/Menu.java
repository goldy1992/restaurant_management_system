/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.CardLayout;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;

/**
 *
 * @author mbbx9mg3
 */
public class Menu extends javax.swing.JDialog implements ActionListener
{
    ArrayList<JComponent> components = new ArrayList<JComponent>();
    ArrayList<JButton> buttons = new ArrayList<JButton>();
    ArrayList<MenuItemJButton> menuItemButtons = new ArrayList<MenuItemJButton>();
    ArrayList<JPanel> panels = new ArrayList<JPanel>();
    
    private JButton SendOrderButton = null;
    
    public static JTextPane myOutput;

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource() == SendOrderButton)
        {
            try 
            {
                con.close();
            } catch (SQLException ex) 
            {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();;
        }
    }

    public static enum MenuArea
    {
        MAIN_PAGE, FOOD_PAGE
    }
    
    // the database connection
    Connection con = null;    

    /**
     * Creates new form Menu
     */
    public Menu(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);

        try 
        {         
            con = DriverManager.getConnection("jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3", "mbbx9mg3", "Fincherz+2013");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initComponents();
        ArrayList<JPanel> cardPanels = getCards();
        cardPanels.set(0, initialiseMainCard(cardPanels.get(0)));
        CardLayout cl = (CardLayout)(CardPanel.getLayout());
        cl.show(CardPanel, "mainCard");
        
        // this code only allows the output Area text pane to have key controls
        components.addAll(buttons);
        components.addAll(menuItemButtons);
        components.addAll(panels);
        
        for (JComponent t : components)
        {
            t.setFocusable(false);
            t.requestFocus(false);
        }       
    } // constructor

    private JPanel initialiseMainCard(JPanel panel)
    {

        System.out.println("initialise main card called");
        panel.setLayout(new java.awt.GridLayout(1, 0));

        JPanel MenuSelectPanel = new JPanel(); 
        MenuSelectPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MenuSelectPanel.setFocusable(false);
        MenuSelectPanel.setRequestFocusEnabled(false);
        
        // EDIT THIS
        MenuSelectPanel.setLayout(new java.awt.GridLayout(5, 2));


        panel.add(MenuSelectPanel);
        panels.add(MenuSelectPanel);

        JPanel FoodMenuPanel = new JPanel();
        FoodMenuPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        
        // EDIT
        FoodMenuPanel.setLayout(new java.awt.GridLayout(8, 3));
        panel.add(FoodMenuPanel);
        panels.add(FoodMenuPanel);

        ArrayList<MenuItemJButton> buttonsForPanel =  getButtonsForPanel(MenuArea.MAIN_PAGE);

        for (int i = 0; i < buttonsForPanel.size(); i++)
            FoodMenuPanel.add(buttonsForPanel.get(i));

        menuItemButtons.addAll(buttonsForPanel);

        JPanel BillHandlePanel = new JPanel();
        BillHandlePanel.setBorder(new javax.swing.border.MatteBorder(null));
        BillHandlePanel.setFocusable(false);
        BillHandlePanel.setRequestFocusEnabled(false);
        BillHandlePanel.setLayout(new java.awt.GridLayout(8, 1));

        JButton PrintBillButton = new JButton(); 
        PrintBillButton.setText("Print Bill");
        BillHandlePanel.add(PrintBillButton);
        buttons.add(PrintBillButton);

        JButton PrintLastReceiptButton = new JButton(); 
        PrintLastReceiptButton.setText("Print Last Receipt");
        BillHandlePanel.add(PrintLastReceiptButton);
        buttons.add(PrintLastReceiptButton);

        JButton VoidButton = new JButton();
        VoidButton.setText("Void");
        BillHandlePanel.add(VoidButton);
        buttons.add(VoidButton);

        JButton VoidLastItemButton = new JButton();
        VoidLastItemButton.setText("Void Last Item");
        BillHandlePanel.add(VoidLastItemButton);
        buttons.add(VoidLastItemButton);
        
        JButton SplitBillButton = new JButton();
        SplitBillButton.setText("Split Bill");
        BillHandlePanel.add(SplitBillButton);
        buttons.add(SplitBillButton);
        
        JButton OrderHoldButton = new JButton();
        OrderHoldButton.setText("Order On Hold");
        BillHandlePanel.add(OrderHoldButton);
        buttons.add(OrderHoldButton);
        
        JButton DeliveredButton = new JButton();
        DeliveredButton.setText("Delivered");
        BillHandlePanel.add(DeliveredButton);
        buttons.add(DeliveredButton);
        
        SendOrderButton = new JButton();
        SendOrderButton.addActionListener(this);
        
        SendOrderButton.setText("Send Order");
        BillHandlePanel.add(SendOrderButton);
        buttons.add(SendOrderButton);

        panel.add(BillHandlePanel);

        CardPanel.add(panel, "mainCard");
        panels.add(panel);
        return panel;
        
    } // initialiseCards
    
    
    private ArrayList<JPanel> getCards()
    {
        ArrayList<JPanel> cardPanels = new ArrayList<JPanel>();
        cardPanels.add(new JPanel());        
        cardPanels.get(0).setName("MAIN_PAGE");

        try 
        {
            PreparedStatement numberOfButtonsQuery = null;
            String query =  "SELECT NAME \n" +
                        "FROM `3YP_MENU_PAGES`\n" +
                        "WHERE PARENT_PAGE_ID IS NOT NULL";
            numberOfButtonsQuery = con.prepareStatement(query);
            numberOfButtonsQuery.executeQuery();
            ResultSet x = numberOfButtonsQuery.getResultSet();
            
         
            while (x.next())
            {
                JPanel panel = new JPanel();
                panel.setName(x.getString(1));
                panel.setFocusable(false);
                panel.setRequestFocusEnabled(false);
                panel.setLayout(new java.awt.GridLayout(1, 0));
                cardPanels.add(panel);
            } // while
            
            // the first panel has already been added hence start from 1
            for (int i = 1; i < cardPanels.size(); i++ )
            {
                CardPanel.add(cardPanels.get(i), cardPanels.get(i).getName());
                panels.add(cardPanels.get(i));
            } // for
        
        } // try
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
                
                return cardPanels;
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        FormPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OutputArea = new javax.swing.JTextPane();
        CardPanel = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        FormPanel.setPreferredSize(new java.awt.Dimension(400, 500));
        FormPanel.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setFocusable(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 150));

        myOutput = OutputArea;
        OutputArea.setEditable(false);
        OutputArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OutputAreaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(OutputArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 2652, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        FormPanel.add(jPanel1, gridBagConstraints);
        panels.add(jPanel1);

        CardPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CardPanel.setPreferredSize(new java.awt.Dimension(400, 350));
        CardPanel.setLayout(new java.awt.CardLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.7;
        FormPanel.add(CardPanel, gridBagConstraints);
        panels.add(CardPanel);

        jMenu1.setText("File");
        MenuBar.add(jMenu1);

        jMenu2.setText("Edit");
        MenuBar.add(jMenu2);

        setJMenuBar(MenuBar);
        components.add(MenuBar);

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

        panels.add(FormPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private MenuItemJButton createMenuItemJButton(String text)
    {
        MenuItemJButton x = new MenuItemJButton(text);
        x.addActionListener(x);
        return x;
    }
    
    private ArrayList<MenuItemJButton> getButtonsForPanel(MenuArea part)
    {
        ArrayList<MenuItemJButton> buttons = new ArrayList<MenuItemJButton>();
        
        try 
        {
            PreparedStatement numberOfButtonsQuery = null;
            String query =  "SELECT NAME \n" +
                        "FROM `3YP_ITEMS`\n" +
                        "LEFT JOIN`3YP_POS_IN_MENU`\n" +
                        "ON 3YP_ITEMS.ID = 3YP_POS_IN_MENU.ID\n" +
                        "WHERE 3YP_POS_IN_MENU.LOCATION = '" + part + "'";
            numberOfButtonsQuery = con.prepareStatement(query);
            numberOfButtonsQuery.executeQuery();
            ResultSet x = numberOfButtonsQuery.getResultSet();
            
         
            while (x.next())
            {
                String result = x.getString("NAME");
                System.out.println(result);
                buttons.add( createMenuItemJButton(result));
            } // while
        } // try
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
        
        return buttons;
    }
    
    private void OutputAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OutputAreaKeyPressed
System.out.println("pressed");
    }//GEN-LAST:event_OutputAreaKeyPressed

    public String currentCard = "mainCard";
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Menu dialog = new Menu(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel CardPanel;
    private javax.swing.JPanel FormPanel;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JTextPane OutputArea;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
    private void switchCards()
    {
                    switch(currentCard)
            {
                case "foodCard":
                    CardLayout x = (CardLayout)CardPanel.getLayout();
                    currentCard = "mainCard";
                    x.show(CardPanel, "mainCard");
                    break;
                default: break;
            }
        
    }

}