package Client;

import static Client.MyClient.serverAddress;
import static Message.Message.generateRequestID;
import Item.Item;
import Item.Tab;
import Message.EventNotification.*;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JDialog;
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
 * <p>This class displays the Menu Dialog box of the restaurant management System.
 * It contains a menu bar of which underneath is the main JPanel called the 
 * FormPanel. The form panel contains two extra panels called the OutputAreaPanel, 
 * and the card panel.</p>
 * 
 * <p><b>OutputAreaPanel</b> - When an item is selected on the menu it will appear
 * in the panel's JFrame along with it's price and the amount that has been 
 * ordered; the total price will appear too. A scroll bar implementation will 
 * also be included to see previous purchases that have disappeared from the 
 * screen.</p>
 * 
 * <p><b>CardPanel</b> - The car panel will contain the actual Menu; it works 
 * as a <a href="http://docs.oracle.com/javase/7/docs/api/java/awt/CardLayout.html">CardLayout</a>
 * in a tree format. The page you see originally is the main panel and has no parent,
 * i.e. it is the root of the card tree. Every other panel has one parent and when
 * an area on the screen that is not a menu item is clicked the card shown will
 * change to the current panel's parent. Each card will contain all the relevant
 * items and all this information will be requested from the database during the
 * creation of the menu.</p>
 *  
 * @author mbbx9mg3
 * 
 * 
 */
public class Menu extends JDialog implements ActionListener, MouseListener
{
    private final ArrayList<JComponent> components = new ArrayList<>();
    private final ArrayList<JButton> buttons = new ArrayList<>();
    private final ArrayList<JPanel> panels = new ArrayList<>();  
    private final ArrayList<MenuItemJButton> menuItemButtons = new ArrayList<>();
    private final Tab tab;
    private final ObjectOutputStream out;
    private final ArrayList<Item> newFoodItems = new ArrayList<>();
    private final ArrayList<Item> newDrinkItems = new ArrayList<>();
    private final ArrayList<Item> newItems = new ArrayList<>();
    private final MenuCardPanel kitchenBarMsgPanel;
    
    private ArrayList<MenuCardPanel> cardPanels = new ArrayList<>();
    private JButton SendOrderButton = null;
    /**
     * Stores the reference to the JTextPane used on the output
     * @see javax.swing.JTextPane
     */
    public JTextPane outputTextPane;
    
    public static boolean isNumeric(String x)
    {
        try
        {
            Integer.parseInt(x);
            return true;
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
    } // isNumeric
    
    /**
     * The method that makes the Kitchen/Bar message card with the keyboard
     * 
     * @return the newly created card
     */
    private MenuCardPanel createKitchenBarMessageCard()
    {
        MenuCardPanel containerPanel = MenuCardPanel.createMenuCardPanel();
        containerPanel.setName("kitchenBarPanel");
        containerPanel.removeAll();
        
        String[] ch = {"Q","W","E","R","T","Y","U","I","O","P",
                     "A","S","D","F","G","H","J","K", "L",
                     "Z","X","C","V","B","N","M",
                     "SPACE"};
        
        JPanel keyboardContainerPanel = new JPanel();
        keyboardContainerPanel.setLayout(new GridLayout(4,1));
        
        containerPanel.add(keyboardContainerPanel);
        containerPanel.add(containerPanel.getKeypadPanel());
        
        
        // add line 1
        JPanel line1 = new JPanel();
        line1.setLayout(new GridLayout(1,10));
        keyboardContainerPanel.add(line1);        
        for(int i = 0; i <= 9; i++)
        {
            JButton key = new JButton(ch[i]);
            key.addActionListener(makeKeyActionListener(ch[i]));
            line1.add(key);
        } // for 
       
        // add line 2
        JPanel line2 = new JPanel();
        line2.setLayout(new GridLayout(1,10));
        keyboardContainerPanel.add(line2);
        for(int i = 10; i <= 18; i++)
        {
            JButton key = new JButton(ch[i]);
            key.addActionListener(makeKeyActionListener(ch[i]));
            line2.add(key);
        }
               
        // add line 3
        JPanel line3 = new JPanel();
        line3.setLayout(new GridLayout(1,10));
        keyboardContainerPanel.add(line3);
        for(int i = 19; i <= 25; i++)
        {
            JButton key = new JButton(ch[i]);
            key.addActionListener(makeKeyActionListener(ch[i]));            
            line3.add(key);
        }
 
        JPanel line4 = new JPanel();
        line4.setLayout(new GridLayout(1,1));
        keyboardContainerPanel.add(line4);
        // La ultima en la colección
        JButton key = new JButton("SPACE");
        key.addActionListener(makeKeyActionListener(" "));
        line4.add(key);
        
        return containerPanel;
    }
    
        /**
     * Creates new form Menu
     * @param parent
     * @param modal
     * @param tab
     * @param stream
     * @throws java.sql.SQLException
     */
    public Menu(java.awt.Frame parent, boolean modal, Tab tab, ObjectOutputStream stream) throws SQLException
    {
        super(parent, modal);
        this.tab = tab;
        this.out = stream;
     
        // initialise the connection to the database
        con = DriverManager.getConnection("jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3", "mbbx9mg3", "Fincherz+2013");
      
        // initialises the part of the GUI made automatically by netbeans
        initComponents();
        
        // create the kitchen/Bar message panel
        kitchenBarMsgPanel = createKitchenBarMessageCard();
        // add the kitchen/Bar message panel to the card layout
        CardPanel.add(kitchenBarMsgPanel, kitchenBarMsgPanel.getName());   

        // prepare the rest of the cards and store in cardPanels arrayList
        cardPanels = getCards();
        
        // initialise the cardLayout to show the main panel
        cardPanels.set(0, initialiseMainCard(cardPanels.get(0)));
        
        
        for(MenuCardPanel p : cardPanels)
            CardPanel.add(p, p.getName());
        
        //MyClient.debugGUI.addText("show");
        CardLayout cl = (CardLayout)(CardPanel.getLayout());
        cl.show(CardPanel, cardPanels.get(0).getName());
        currentCard = cardPanels.get(0);
        
        // set the kitchen parent not because the current card is not null
        kitchenBarMsgPanel.setParentPanel(currentCard);
        
        // this code only allows the output Area text pane to have key controls
        components.addAll(buttons);
        components.addAll(menuItemButtons);
        components.addAll(cardPanels);
        
        for (JComponent t : components)
        {
            t.setFocusable(false);
            t.requestFocus(false);
        } // for   
        
        outputTextPane.setText(tab.toString());
        
    } // constructor

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource() == SendOrderButton)
        {
            
            
            try 
            {
                TabUpdateNfn newEvt = new TabUpdateNfn(InetAddress.getByName(
                    MyClient.client.getLocalAddress().getHostName()),
                    InetAddress.getByName(serverAddress.getHostName()),
                    generateRequestID(), this.tab);
                out.reset();
                out.writeObject(newEvt);
                
                // send the new items to the bar or kitchen respectively
                if (this.newDrinkItems.size() > 0)
                {
                    NewItemNfn newEvt1 = new NewItemNfn(InetAddress.getByName(
                        MyClient.client.getLocalAddress().getHostName()),
                        InetAddress.getByName(serverAddress.getHostName()),
                        generateRequestID(), 
                        Item.Type.DRINK, 
                        this.newDrinkItems,
                        tab.getTable());
                        out.reset();
                        out.writeObject(newEvt1);
                        MyClient.debugGUI.addText("sent drinks");
                } // if
                
                if (this.newFoodItems.size() > 0)
                {
                    NewItemNfn newEvt1 = new NewItemNfn(InetAddress.getByName(
                        MyClient.client.getLocalAddress().getHostName()),
                        InetAddress.getByName(serverAddress.getHostName()),
                        generateRequestID(), 
                        Item.Type.FOOD, 
                        this.newFoodItems,
                        tab.getTable());
                    out.reset();
                    out.writeObject(newEvt1);
                    MyClient.debugGUI.addText("sent food");
                } // if                
                
                con.close(); 
            } // try
            catch (SQLException | IOException ex) 
            { 
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } // catch
            this.dispose();
        } // if
    } // actionPerformed
    
    public MenuCardPanel getKitchenBarMsgPanel()
    {
        return kitchenBarMsgPanel;
    }
    
    public Tab getTab()
    {
        return tab;
    }

    @Override
    public void mouseClicked(MouseEvent me) 
    {
        //MyClient.debugGUI.addText("mouse clicked");
        //MyClient.debugGUI.addText("source class: " +  me.getSource().getClass());
            if(me.getSource() == this || me.getSource() == outputTextPane)
            {
                //MyClient.debugGUI.addText("found called panel");
                switchToParentCard();
            }   
    } // mouseClickeds

    @Override
    public void mousePressed(MouseEvent me) {  }

    @Override
    public void mouseReleased(MouseEvent me) {  }

    @Override
    public void mouseEntered(MouseEvent me) { }

    @Override
    public void mouseExited(MouseEvent me) { }

    
    /**
     * The reference to the object that stores the database connection
     */
    Connection con = null;    

    
    /**
     * Adds the functional features to the main panel
     * @param the main panel in the set of panels
     * @return the main panel with the extra buttons added to it
     */
    private MenuCardPanel initialiseMainCard(MenuCardPanel panel)
    {

        // creates the panel that everything will be created on
        JPanel BillHandlePanel = new JPanel();
        
        BillHandlePanel.setBorder(new javax.swing.border.MatteBorder(null));
        BillHandlePanel.setFocusable(false);
        BillHandlePanel.setRequestFocusEnabled(false);
        BillHandlePanel.setLayout(new java.awt.GridLayout(9, 1));

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
        
        JButton barTabButton = new JButton();
        barTabButton.setText("Bar Tab");
        BillHandlePanel.add(barTabButton);
        buttons.add(barTabButton);
        
        SendOrderButton = new JButton();
        SendOrderButton.addActionListener(this);
        SendOrderButton.setText("Send Order");
        BillHandlePanel.add(SendOrderButton);
        buttons.add(SendOrderButton);

        panel.remove(2);
        panel.add(BillHandlePanel);
        panel.add(Menu.createKeypadPanel());

        CardPanel.add(panel, panel.getName());
        panels.add(panel);
        return panel;
        
    } // initialiseCards    
    
    private ArrayList<MenuCardPanel> getCards()
    {
        ArrayList<MenuCardPanel> cardPanelsList = new ArrayList<>();

        try 
        {
            // PREPARE SELECT STATEMENT TO SELECT MENU PAGES TABLE
            PreparedStatement numberOfButtonsQuery = null;
            String query =  "SELECT * \n" +
                        "FROM `3YP_MENU_PAGES`";
            numberOfButtonsQuery = con.prepareStatement(query);
            numberOfButtonsQuery.executeQuery();
            ResultSet results = numberOfButtonsQuery.getResultSet();

            // MAKE AN OBJECT FOR EVERY VIEW CARD PANEL
            while (results.next())
            {
                MenuCardPanel panel = MenuCardPanel.createMenuCardPanel();
                panel.setName(results.getString(1));
                cardPanelsList.add(panel);
                
                // ADD the kitchen message card option to EVERY card's children
                panel.addChildCardButton(MenuCardLinkJButton.createMenuCardLinkButton(kitchenBarMsgPanel, "Kitchen/Bar Message"));
            } // while
                            
             // ADD EVERY CARD'S PARENT AND CHILDREN PANELS
            int count = 0; // keeps count to remember current panel
            results.first(); // uses same resultsSet and goes to the first row
            do
            {
                MenuCardPanel currentPanel = cardPanelsList.get(count);
                MenuCardPanel parentPanel = null;
                
                // for loop to find parent panel
                for (MenuCardPanel c : cardPanelsList )
                    if (c.getName().equals(results.getString(2))) // 2 is column index of PARENT_PAGE_ID
                        parentPanel = c;
                
                // set currentPanels Parent {COULD BE NULL}
                currentPanel.setParentPanel(parentPanel);  
                
                // if not null take the parent and create a child button and reference it to panel
                if (parentPanel != null)
                    parentPanel.addChildCardButton(MenuCardLinkJButton.createMenuCardLinkButton(currentPanel, 
                            results.getString(3)));
                
                count++;
            }  while (results.next());
            
            // adds all c
            for (MenuCardPanel c1 : cardPanelsList)
                c1.addAllChildCardButtonsToPanel();
            
                
            
            // FIND ALL BUTTONS FOR EACH PANEL
            for (MenuCardPanel c : cardPanelsList )
            {
                query = "SELECT `NAME`, `3YP_ITEMS`.`ID`, `PRICE`, `FOOD_OR_DRINK` FROM `3YP_ITEMS` " 
                    + "LEFT JOIN `3YP_POS_IN_MENU` ON "
                    + "`3YP_ITEMS`.`ID` = `3YP_POS_IN_MENU`.`ID` "
                    + "WHERE `3YP_POS_IN_MENU`.`LOCATION` = \"" + c.getName() + "\" ";
                
                numberOfButtonsQuery = con.prepareStatement(query);
                numberOfButtonsQuery.executeQuery();
                results = numberOfButtonsQuery.getResultSet();
                
                while(results.next())
                {
                    MenuItemJButton newButton = MenuItemJButton.createMenuItemJButton(results.getString(1), 
                            results.getInt(2), new BigDecimal(results.getDouble(3)), results.getString(4));
                    c.addMenuItemButton(newButton);          
                } // while
                
                c.addAllItemsToPanel();
            } // for each
  
            // CODE TO MOVE MAIN PAGE TO THE FRONT OF THE ARRAYLIST            
            for (int i = 1; i < cardPanelsList.size(); i++)
                if (cardPanelsList.get(i).getName().equals("MAIN_PAGE"))
                    Collections.swap(cardPanelsList, i, 0);
                
           // for (MenuCardPanel c : cardPanelsList )
                //MyClient.debugGUI.addText(c);            
        } // try // try
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
                
        return cardPanelsList;      
    } // getCards
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        FormPanel = new javax.swing.JPanel();
        OutputAreaPanel = new javax.swing.JPanel();
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

        OutputAreaPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        OutputAreaPanel.setFocusable(false);
        OutputAreaPanel.setPreferredSize(new java.awt.Dimension(400, 150));

        outputTextPane = OutputArea;
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

        javax.swing.GroupLayout OutputAreaPanelLayout = new javax.swing.GroupLayout(OutputAreaPanel);
        OutputAreaPanel.setLayout(OutputAreaPanelLayout);
        OutputAreaPanelLayout.setHorizontalGroup(
            OutputAreaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );
        OutputAreaPanelLayout.setVerticalGroup(
            OutputAreaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        FormPanel.add(OutputAreaPanel, gridBagConstraints);
        panels.add(OutputAreaPanel);

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

    
    private void OutputAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OutputAreaKeyPressed
MyClient.debugGUI.addText("pressed");
    }//GEN-LAST:event_OutputAreaKeyPressed

    private void OutputAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OutputAreaMouseClicked
        switchToParentCard();
    }//GEN-LAST:event_OutputAreaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CardPanel;
    private javax.swing.JPanel FormPanel;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JTextPane OutputArea;
    private javax.swing.JPanel OutputAreaPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
    /**
     *
     */
    public MenuCardPanel currentCard;

    /**
     * Should be called when the screen detects a click that is not on an item
     */ 
    public void switchToParentCard()
    {
        if(this.currentCard.hasParent())
        {
            //MyClient.debugGUI.addText("current panel " + this.currentCard.getName());
            int parentIndex = cardPanels.indexOf(this.currentCard.getParentPanel());
            CardLayout cl = (CardLayout)(CardPanel.getLayout());
            cl.show(CardPanel, cardPanels.get(parentIndex).getName() );
            currentCard = cardPanels.get(parentIndex);
            
            
            /* TO DOOOO
            if  new items is not nulll
            
                If current panel == kitch message
                    parse last line and add the last items kitch message
            
            
            
            */
            
            
            // set the new Kitchen message parent to be the new card.
            kitchenBarMsgPanel.setParentPanel(currentCard);
            
            Menu.removeNumberFromTab();
        } // if
    } // switchToParentCard
    
    /**
     * 
     * @return the JPanel that stores all the cards
     */
    public JPanel getCardPanel() { return CardPanel; } 
       
    /**
    *
    * Factory method to make a new menu
     * @param parent
     * @param tab
     * @param out
     * @return 
    */
    public static Menu makeMenu(JFrame parent, Tab tab, ObjectOutputStream out)
    {
        Menu newMenu = null;
        try 
        {

            newMenu = new Menu(parent, true, tab, out);
            newMenu.addMouseListener(newMenu);

        } // try
        catch (SQLException ex) 
        {
            Logger.getLogger(SelectTable.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
        
        return newMenu;
    } // makeMenu
    
    public void addNewItem(Item newItem)
    {
        this.newItems.add(newItem);
    } // addNewItem
    
    public static JPanel createKeypadPanel()
    {
        JPanel keypadPanel = new JPanel();
        keypadPanel.setLayout(new GridLayout(4,3));
        
        // makes the first 9 buttons
        for (int i= 1; i <= 9; i++)
        {
            final int x = i; // declared final so can be used in the actionlistener method
            JButton number = new JButton(i + "");
            number.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    String currentTab = MyClient.selectTable.menu.outputTextPane.getText();
                    currentTab += x;
                    MyClient.selectTable.menu.outputTextPane.setText(currentTab);                   
                } // act performed
            });
            keypadPanel.add(number);
        } // for
        
        /* make the zero button manually after number 9 */   
        final int zero = 0; // declared final so can be used in the actionlistener method
        JButton number = new JButton(zero + "");
        number.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String currentTab = MyClient.selectTable.menu.outputTextPane.getText();
                currentTab += zero;
                MyClient.selectTable.menu.outputTextPane.setText(currentTab);    
            }
        });
        keypadPanel.add(number);
        
        
        /* Make the clear button */
        JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Menu.removeNumberFromTab();
            } // actionPerformed
        });
        keypadPanel.add(clear);    
        
        return keypadPanel;
    }
    
    /**
     * A useful method that will take any number to express quantity that have 
     * been pressed and remove them, reseting the default number to 1.
     */
    public static void removeNumberFromTab()
    {
        // detects to see if a number has been pressed and if so removes it
        String currentTab = MyClient.selectTable.menu.outputTextPane.getText();
        String[] array = currentTab.split("\n");
        for(String s : array)
            System.out.println(s);
    
        if (isNumeric(array[array.length-1]))
        {
            String x = "";
            for (int i = 0; i <= array.length - 2; i++)
                x += array[i] + "\n";

            MyClient.selectTable.menu.outputTextPane.setText(x);
        } // if
    } // remove number from tab
    
    private ActionListener makeKeyActionListener(final String key)
    {
    
        ActionListener toReturn = new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    Menu menu = MyClient.selectTable.menu;
                    String currentText = menu.OutputArea.getText();
                    currentText += key;
                    menu.OutputArea.setText(currentText);
                   
                }
        };
        
        return toReturn;
        
    }
    
} // class