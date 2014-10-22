package Client;


import Client.DatabaseConnect;
import Client.MenuCardLinkJButton;
import Client.MenuCardPanel;
import Client.MenuGUI;
import Client.MenuItemJButton;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Goldy
 */
public class Menu 
{
    // the database connection
    Connection con = null; 
    
    ArrayList<JComponent> components = new ArrayList<JComponent>();
    ArrayList<JButton> buttons = new ArrayList<JButton>();
    ArrayList<MenuItemJButton> menuItemButtons = new ArrayList<MenuItemJButton>();
    ArrayList<MenuCardPanel> cardPanels = new ArrayList<MenuCardPanel>();
    ArrayList<JPanel> panels = new ArrayList<JPanel>();
    private JButton SendOrderButton = null;   
    public JTextPane myOutput;
    public String currentCard = "mainCard";
    private JPanel CardPanel;
    private GridBagConstraints cardPanelGridBagConstraints;    
    private JPanel OutputAreaPanel;
    private JScrollPane jScrollPane1;
    private JTextPane OutputArea;
    private GridBagConstraints outputPanelgridBagConstraints;
    
    public Menu()
    {
        this.initialiseCardPanel();   
        for(MenuCardPanel p : cardPanels)
            CardPanel.add(p);
        
        //cardPanels.set(0, initialiseMainCard(cardPanels.get(0)));
        CardLayout cl = (CardLayout)(CardPanel.getLayout());
        cl.show(CardPanel, cardPanels.get(0).getName());
        
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
    
    private void initialiseCardPanel()
    {
        CardPanel = new javax.swing.JPanel();
        CardPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CardPanel.setPreferredSize(new java.awt.Dimension(400, 350));
        CardPanel.setLayout(new java.awt.CardLayout());
        cardPanelGridBagConstraints = new GridBagConstraints();
        cardPanelGridBagConstraints.gridx = 0;
        cardPanelGridBagConstraints.gridy = 1;
        cardPanelGridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        cardPanelGridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        cardPanelGridBagConstraints.weightx = 1.0;
        cardPanelGridBagConstraints.weighty = 0.7;
        panels.add(CardPanel);        
        cardPanels = getCards();
        
    }
    
    private void initialiseOutputAreaPanel()
    {
                OutputAreaPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OutputArea = new javax.swing.JTextPane();
                OutputAreaPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        OutputAreaPanel.setFocusable(false);
        OutputAreaPanel.setPreferredSize(new java.awt.Dimension(400, 150));

        myOutput = OutputArea;
        OutputArea.setEditable(false);
        OutputArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                System.out.println("pressed");
            }
        });
        jScrollPane1.setViewportView(OutputArea);

        javax.swing.GroupLayout OutputAreaPanelLayout = new javax.swing.GroupLayout(OutputAreaPanel);
        OutputAreaPanel.setLayout(OutputAreaPanelLayout);
        OutputAreaPanelLayout.setHorizontalGroup(
            OutputAreaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );
        OutputAreaPanelLayout.setVerticalGroup(
            OutputAreaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
        );
        outputPanelgridBagConstraints = new java.awt.GridBagConstraints();
        outputPanelgridBagConstraints.gridx = 0;
        outputPanelgridBagConstraints.gridy = 0;
        outputPanelgridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        outputPanelgridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        outputPanelgridBagConstraints.weightx = 1.0;
        outputPanelgridBagConstraints.weighty = 0.3;
    }

    public static enum MenuArea
    {
        MAIN_PAGE, FOOD_PAGE
    }
    
    private MenuCardPanel initialiseMainCard(MenuCardPanel panel)
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
        //SendOrderButton.addActionListener(this);
        SendOrderButton.setText("Send Order");
        BillHandlePanel.add(SendOrderButton);
        buttons.add(SendOrderButton);

        panel.add(BillHandlePanel);

        CardPanel.add(panel, "mainCard");
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
                count++;
            }  while (x.next());
            
            
            
            
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
            
            
            
            // FIND ALL CHILD PANELS
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
    
    public JPanel getCardPanel()
    {
        return CardPanel;
    }
    
    public JButton getSendOrderButton()
    {
        return SendOrderButton;
    }
    
    
    
    
    
    
    
    // factory Methods
    
    private MenuItemJButton createMenuItemJButton(String text)
    {
        MenuItemJButton x = new MenuItemJButton(text);
        x.addActionListener(x);
        return x;
    }
    
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
