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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JTextPane;

/**
 *
 * @author mbbx9mg3
 */
public class Menu extends javax.swing.JDialog implements ActionListener, MouseListener
{
    private ArrayList<JComponent> components = new ArrayList<JComponent>();
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<MenuItemJButton> menuItemButtons = new ArrayList<MenuItemJButton>();
    private ArrayList<MenuCardPanel> cardPanels = new ArrayList<MenuCardPanel>();
    private ArrayList<JPanel> panels = new ArrayList<JPanel>();
    
    private JButton SendOrderButton = null;
    
    public JTextPane myOutput;

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

    @Override
    public void mouseClicked(MouseEvent me) 
    {
        System.out.println("mouse clicked");
        System.out.println("source class: " +  me.getSource().getClass());
            if(me.getSource() == this || me.getSource() == myOutput)
            {
                System.out.println("found called panel");
                switchCards();
            }
        
    }

    @Override
    public void mousePressed(MouseEvent me) {  }

    @Override
    public void mouseReleased(MouseEvent me) {  }

    @Override
    public void mouseEntered(MouseEvent me) { }

    @Override
    public void mouseExited(MouseEvent me) { }

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
        cardPanels = getCards();
        cardPanels.set(0, initialiseMainCard(cardPanels.get(0)));
        
        for(MenuCardPanel p : cardPanels)
            CardPanel.add(p, p.getName());
        
        System.out.println("show");
        CardLayout cl = (CardLayout)(CardPanel.getLayout());
        cl.show(CardPanel, cardPanels.get(0).getName());
        currentCardName = cardPanels.get(0).getName();
        
        // this code only allows the output Area text pane to have key controls
        components.addAll(buttons);
        components.addAll(menuItemButtons);
        components.addAll(cardPanels);
        
        for (JComponent t : components)
        {
            t.setFocusable(false);
            t.requestFocus(false);
        }    
        
 
    } // constructor

    
    private MenuCardPanel initialiseMainCard(MenuCardPanel panel)
    {

        System.out.println("initialise main card called");

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

        CardPanel.add(panel, panel.getName());
        panels.add(panel);
        return panel;
        
    } // initialiseCards
   
    
    
    private ArrayList<MenuCardPanel> getCards()
    {
        ArrayList<MenuCardPanel> cardPanels = new ArrayList<MenuCardPanel>();

        try 
        {
            // PREPARE SELECT STATEMENT
            PreparedStatement numberOfButtonsQuery = null;
            String query =  "SELECT * \n" +
                        "FROM `3YP_MENU_PAGES`";
            numberOfButtonsQuery = con.prepareStatement(query);
            numberOfButtonsQuery.executeQuery();
            ResultSet x = numberOfButtonsQuery.getResultSet();

            
            
            // MAKE AN OBJECT FOR EVERY VIEW CARD PANEL
            while (x.next())
            {
                MenuCardPanel panel = createMenuCardPanel();
                panel.setName(x.getString(1));
                cardPanels.add(panel);
            } // while
           
                for (MenuCardPanel c : cardPanels )
                    System.out.println(c.getName());
            
                
                
                
                
             // ADD EVERY CARD VIEW PANEL PARENT
            int count = 0;
            x.first();           
            do
            {
                MenuCardPanel currentPanel = cardPanels.get(count);
                MenuCardPanel parentPanel = null;
                
                for (MenuCardPanel c : cardPanels )
                    if (c.getName().equals(x.getString(2)))
                        parentPanel = c;
                
                if (parentPanel == null)
                    System.out.println("current panel = " + currentPanel.getName() +
                        "; parentPanel = null");
                else
                    System.out.println("current panel = " + currentPanel.getName() +
                        "; parentPanel = " + parentPanel.getName());
                
                currentPanel.setParentPanel(parentPanel);  
                
                if (parentPanel != null)
                    parentPanel.addChildCardButton(createMenuCardLinkButton(currentPanel));
                count++;
            }  while (x.next());
            
            for (MenuCardPanel c1 : cardPanels)
                c1.addAllChildCardButtonsToPanel();
            
            
            
            
            // FIND ALL BUTTONS FOR EACH PANEL
            for (MenuCardPanel c : cardPanels )
            {
                query = "SELECT NAME FROM `3YP_ITEMS` "
                    + "LEFT JOIN `3YP_POS_IN_MENU` ON "
                    + "`3YP_ITEMS`.`ID` = `3YP_POS_IN_MENU`.`ID` "
                    + "WHERE `3YP_POS_IN_MENU`.`LOCATION` = \"" + c.getName() + "\" ";
                
                numberOfButtonsQuery = con.prepareStatement(query);
                numberOfButtonsQuery.executeQuery();
                x = numberOfButtonsQuery.getResultSet();
                
                while(x.next())
                {
                    MenuItemJButton newButton = new MenuItemJButton(x.getString(1));
                    c.addMenuItemButton(newButton);          
                }
                
                c.addAllItemsToPanel();
            } // for each
            
            
            
            /* FIND ALL CHILD PANELS
            for (MenuCardPanel c : cardPanels )
            {
                query = "SELECT NAME FROM `3YP_MENU_PAGES` "
                        + "WHERE `PARENT_PAGE_ID` = \"" + c.getName() + "\""; 
                
                numberOfButtonsQuery = con.prepareStatement(query);
                numberOfButtonsQuery.executeQuery();
                x = numberOfButtonsQuery.getResultSet();
                
                while(x.next())
                    for (MenuCardPanel c1 : cardPanels)
                        if (c1.getName().equals(x.getString(1)))
                        {
                            MenuCardLinkJButton newButton = createMenuCardLinkButton(c1);
                            c.addChildCardButton(newButton);
                        }
                
                // adds all the buttons to the panel
                c.addAllChildCardButtonsToPanel();
            } // for each
            */
  
            // CODE TO MOVE MAIN PAGE TO THE FRONT OF THE ARRAYLIST            
            for (int i = 1; i < cardPanels.size(); i++)
                if (cardPanels.get(i).getName().equals("MAIN_PAGE"))
                    Collections.swap(cardPanels, i, 0);
                
             for (MenuCardPanel c : cardPanels )
                 System.out.println(c);    
        
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
        OutputArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OutputAreaMouseClicked(evt);
            }
        });
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
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
        CardPanel.setName(""); // NOI18N
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

    private void OutputAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OutputAreaMouseClicked
        // TODO add your handling code here:
        switchCards();
    }//GEN-LAST:event_OutputAreaMouseClicked


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
    
    public String currentCardName;
    public void switchCards()
    {
        System.out.println("switch cards, currentCardName: " + currentCardName);
        for(MenuCardPanel p : cardPanels)
            if (p.getName().equals(currentCardName))
                if(p.hasParent())
                {
                    System.out.println("current panel " + p.getName());
                    int parentIndex = cardPanels.indexOf(p.getParentPanel());
                    CardLayout cl = (CardLayout)(CardPanel.getLayout());
                    cl.show(CardPanel, cardPanels.get(parentIndex).getName() );
                    currentCardName = cardPanels.get(parentIndex).getName();
                }
    }
    
    /**
     * 
     * @return the JPanel that stores all the cards
     */
    public JPanel getCardPanel()
    {
        return CardPanel;
    }
    
    
   
    
    
    // factory Methods
    
    private MenuItemJButton createMenuItemJButton(String text)
    {
        MenuItemJButton x = new MenuItemJButton(text);
        x.addActionListener(x);
        return x;
    }
    
    
    /**
     * A factory method to make a new Menu Card Panel
     * 
     * @return a new Menu Card Panel
     */
    private MenuCardPanel createMenuCardPanel()
    {
        MenuCardPanel x = new MenuCardPanel();
        x.setLayout(new GridLayout(1,0));
        x.add(x.getMenuSelectPanel());
        x.add(x.getItemsPanel());
        x.setFocusable(false);
        x.setRequestFocusEnabled(false);
        return x;
    }
    
    private MenuCardLinkJButton createMenuCardLinkButton(MenuCardPanel parent)
    {
        MenuCardLinkJButton x = new MenuCardLinkJButton(parent);
        x.addActionListener(x);
        return x;
    }

}