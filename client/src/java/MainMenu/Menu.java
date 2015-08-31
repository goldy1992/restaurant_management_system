package Client.MainMenu;

import Client.Client;
import Client.DatabaseConnect;
import Client.Pair;

import Item.Item;
import Item.Tab;
import Message.EventNotification.*;
import Message.Table;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
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
    protected final ArrayList<JComponent> components = new ArrayList<>();
    protected final ArrayList<JButton> buttons = new ArrayList<>();
    protected final ArrayList<JPanel> panels = new ArrayList<>();  
    protected final ArrayList<MenuItemJButton> menuItemButtons = new ArrayList<>();
    protected Tab oldTab;
    protected Tab newTab;
    protected final ObjectOutputStream out;
    protected final MenuCardPanel kitchenBarMsgPanel;
    public int quantitySelected = -1; // -1 defaults to 1
    public boolean messageForLatestItem = false;
    public boolean seenID = false;
    public String lastReceipt = null;
    public String currentBill = null;    
    private ArrayList<MenuCardPanel> cardPanelsList = new ArrayList<>();

    /**
     * Stores the reference to the JTextPane used on the output
     * @see javax.swing.JTextPane
     */
    public JTextPane outputTextPane;
    public JTextPane quantityTextPane;
    public JTextPane totalCostTextPane;
    protected final Client parentClient;
    
    protected String[] getOptionNames() { 
        
       String[] x = {"Print Bill", 
             "Void", "Void Last Item", "Split Bill",  
              "Order On Hold", "Delivered", "Send Order"};
       return x;
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        FormPanel = new javax.swing.JPanel();
        OutputAreaPanel = new javax.swing.JPanel();
        ouputScrollPane = new javax.swing.JScrollPane();
        OutputArea = new javax.swing.JTextPane();
        quantityPane = new javax.swing.JScrollPane();
        quantityArea = new javax.swing.JTextPane();
        totalCostPane = new javax.swing.JScrollPane();
        totalCostArea = new javax.swing.JTextPane();
        CardPanel = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();

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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        FormPanel.setPreferredSize(new java.awt.Dimension(400, 500));
        FormPanel.setLayout(new java.awt.GridBagLayout());

        OutputAreaPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        OutputAreaPanel.setFocusable(false);
        OutputAreaPanel.setPreferredSize(new java.awt.Dimension(400, 150));
        OutputAreaPanel.setLayout(new java.awt.GridBagLayout());

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
        ouputScrollPane.setViewportView(OutputArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 2631;
        gridBagConstraints.ipady = 108;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        OutputAreaPanel.add(ouputScrollPane, gridBagConstraints);

        quantityTextPane = quantityArea;
        quantityPane.setViewportView(quantityArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 0.5;
        OutputAreaPanel.add(quantityPane, gridBagConstraints);

        totalCostTextPane = totalCostArea;
        totalCostPane.setViewportView(totalCostArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        OutputAreaPanel.add(totalCostPane, gridBagConstraints);

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

        getContentPane().add(FormPanel);
        panels.add(FormPanel);

        fileMenu.setText("File");
        MenuBar.add(fileMenu);

        editMenu.setText("Edit");
        MenuBar.add(editMenu);

        setJMenuBar(MenuBar);
        components.add(MenuBar);

        MenuBar.setFocusable(false);
        MenuBar.requestFocus(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void OutputAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OutputAreaKeyPressed
        //parentClient.debugGUI.addText("pressed");
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
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane ouputScrollPane;
    private javax.swing.JTextPane quantityArea;
    private javax.swing.JScrollPane quantityPane;
    private javax.swing.JTextPane totalCostArea;
    private javax.swing.JScrollPane totalCostPane;
    // End of variables declaration//GEN-END:variables
    
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
    protected final MenuCardPanel createKitchenBarMessageCard()
    {
        MenuCardPanel containerPanel = MenuCardPanel.createMenuCardPanel(this);
        containerPanel.setName("kitchenBarPanel");
        containerPanel.removeAll();
        
        String[] ch = {"Q","W","E","R","T","Y","U","I","O","P",
                     "A","S","D","F","G","H","J","K", "L",
                     "Z","X","C","V","B","N","M",
                     "SPACE"};
        
        containerPanel.setLayout(new GridLayout(5,1));
       
        /*  ADD THE NUMBERS */
        JPanel numberLine = new JPanel();
        numberLine.setLayout(new GridLayout(1,10));
        containerPanel.add(numberLine);
        for (int i = 1; i <=10; i++)
        {
            Integer x = i;
            if (x == 10) x = 0;
            JButton key = new JButton(x.toString());
            key.addActionListener(makeKeyActionListener(x.toString(), this));
            numberLine.add(key);
        }
                   
        // add line 1
        JPanel line1 = new JPanel();
        line1.setLayout(new GridLayout(1,10));
        containerPanel.add(line1);        
        for(int i = 0; i <= 9; i++)
        {
            JButton key = new JButton(ch[i]);
            key.addActionListener(makeKeyActionListener(ch[i], this));
            line1.add(key);
        } // for 
       
        // add line 2
        JPanel line2 = new JPanel();
        line2.setLayout(new GridLayout(1,10));
        containerPanel.add(line2);
        for(int i = 10; i <= 18; i++)
        {
            JButton key = new JButton(ch[i]);
            key.addActionListener(makeKeyActionListener(ch[i], this));
            line2.add(key);
        }
               
        // add line 3
        JPanel line3 = new JPanel();
        line3.setLayout(new GridLayout(1,10));
        containerPanel.add(line3);
        for(int i = 19; i <= 25; i++)
        {
            JButton key = new JButton(ch[i]);
            key.addActionListener(makeKeyActionListener(ch[i], this));            
            line3.add(key);
        }
 
        JPanel line4 = new JPanel();
        line4.setLayout(new GridLayout(1,1));
        containerPanel.add(line4);
        // La ultima en la colección
        JButton key = new JButton("SPACE");
        key.addActionListener(makeKeyActionListener(" ", this));
        line4.add(key);
        
        return containerPanel;
    }
    
        /**
     * Creates new form Menu
     * @param parentClient
     * @param parent
     * @param modal
     * @param tab
     * @param stream
     * @throws java.sql.SQLException
     */
    public Menu(Client parentClient, java.awt.Frame parent, boolean modal, Tab tab) throws SQLException
    {
        super(parent, modal);
        this.out = null;
        this.parentClient = parentClient;
     
        // initialise the connection to the database
        con = DriverManager.getConnection("jdbc:mysql://sql4.freemysqlhosting.net:3306/sql482884", "sql482884", "aN9*kG1!");
      
        // initialises the part of the GUI made automatically by netbeans
        initComponents();
        
        // create the kitchen/Bar message panel
        kitchenBarMsgPanel = createKitchenBarMessageCard();
        
        // add the kitchen/Bar message panel to the card layout
        CardPanel.add(kitchenBarMsgPanel, kitchenBarMsgPanel.getName());   

        // prepare the rest of the cards and store in cardPanels arrayList
        cardPanelsList = getCards();
        
        // initialise the cardLayout to show the main panel
        cardPanelsList.set(0, initialiseMainCard(cardPanelsList.get(0)));
                
        for(MenuCardPanel p : cardPanelsList)
        {
            CardPanel.add(p, p.getName());
            p.add(p.createKeypadPanel());
            
        }
        //MyClient.debugGUI.addText("show");
        CardLayout cl = (CardLayout)(CardPanel.getLayout());
        cl.show(CardPanel, cardPanelsList.get(0).getName());
        currentCard = cardPanelsList.get(0);
        
        /* set the kitchen card's parent now so that it is not null when it's it
        first selected */
        kitchenBarMsgPanel.setParentPanel(currentCard);
        
        // this code only allows the output Area text pane to have key controls
        components.addAll(buttons);
        components.addAll(menuItemButtons);
        components.addAll(cardPanelsList);
        
        for (JComponent t : components)
        {
            t.setFocusable(false);
            t.requestFocus(false);
        } // for   
        
        setUpTab(tab);
        
        con.close();
        
    } // constructor
    
    public final void setUpTab(Tab tab)
    {
        if (tab != null)
        {
            outputTextPane.setText(tab.toString());
            // set up current tab
            this.oldTab = tab;
            // sets the table
            this.newTab = new Tab(oldTab.getParent());

        }
        else
        {
            this.oldTab = new Tab(new Table(0));
            this.newTab = new Tab(new Table(0));
        }       
        setTotal();
    }
    
    public void sendOrder()
    {  
        System.out.println("called send order");
        this.oldTab.mergeTabs(newTab);

        try 
        {
            TabUpdateNfn newEvt = new TabUpdateNfn(InetAddress.getByName(
                parentClient.client.getLocalAddress().getHostName()),
                InetAddress.getByName(parentClient.serverAddress.getHostName()),
                 this.oldTab);
            out.reset();
            out.writeObject(newEvt);
                
            // send the new items to the bar or kitchen respectively
            if (this.newTab.getDrinks().size() > 0)
            {
                NewItemNfn newEvt1 = new NewItemNfn(InetAddress.getByName(
                    parentClient.client.getLocalAddress().getHostName()),
                    InetAddress.getByName(parentClient.serverAddress.getHostName()),
                    Item.Type.DRINK, 
                    this.newTab.getDrinks(),
                    newTab.getTable());
                    out.reset();
                    out.writeObject(newEvt1);
            } // if
                
            if (this.newTab.getFood().size() > 0)
            {
                NewItemNfn newEvt1 = new NewItemNfn(InetAddress.getByName(
                    parentClient.client.getLocalAddress().getHostName()),
                    InetAddress.getByName(parentClient.serverAddress.getHostName()),
                    Item.Type.FOOD, 
                    this.newTab.getFood(),
                    newTab.getTable());
                out.reset();
                out.writeObject(newEvt1);
            } // if                
                
            this.seenID = false;
            
            if (!(this instanceof TillMenu))
            {
                TableStatusEvtNfn newEvt1;
                newEvt1 = new TableStatusEvtNfn(InetAddress.getByName(parentClient.client.getLocalAddress().getHostName()),
                InetAddress.getByName(parentClient.serverAddress.getHostName()),
                oldTab.getTable().getTableNumber(), Table.TableStatus.OCCUPIED);
                out.reset();
                out.writeObject(newEvt1);
            }    
                con.close();
        } // try // try
        catch (IOException | SQLException ex) 
        { 
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        // catch
         // catch
        
        this.newTab = new Tab(oldTab.getTable());
    
    } // sendOrder
    
    public String calculateBill()
    {
        Tab methodOldTab = null;   
        Tab methodNewTab = null;
        try 
        {
            methodOldTab = Tab.cloneTab(getOldTab());
            methodNewTab = Tab.cloneTab(getNewTab());    
        } // try
        catch (IOException | ClassNotFoundException ex) 
        {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
        
        if (methodOldTab == null || methodNewTab == null)
            return "";
        
        methodOldTab.mergeTabs(methodNewTab);
        System.out.println("methodOldtab: " + methodOldTab.getItems());

        HashMap<String, Item> billFormat = new HashMap();

        for (Item i : methodOldTab.getItems())
        {
            if (billFormat.containsKey(i.getName()))
            {
                Item item = billFormat.get(i.getName());
                item.setQuantity(item.getQuantity() + i.getQuantity());            
            }  // if
            else
                billFormat.put(i.getName(), new Item(i));      
        } // for
        
        String bill = "";
        double total = 0;
        for (Item item : billFormat.values())
        {
            total += item.getTotalPrice();
            bill += item.firstLineScreenOutput();
        }
        
        DecimalFormat df = new DecimalFormat("0.00");
        bill += "\nTotal: £" + df.format(total) + "\n";
        return bill;      
    } // calculateBill
    
    public void writeBill() throws IOException
    {

        File file = new File("Bill_Table_" + oldTab.getTable().getTableNumber() + ".txt");
        int i = 1;
        while (file.exists())
        {    
            file = new File("Bill_Table_" + oldTab.getTable().getTableNumber() + "(" + i + ")" + ".txt");
            i++;
        }

        // creates the file
        file.createNewFile();
        // Writes the content to the file
        try ( FileWriter writer = new FileWriter(file) ) 
        {
            // Writes the content to the file
            writer.write(this.currentBill);
            writer.flush();

        } catch (IOException ex) {
        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);}    
    } // writeBill
    
    public void printBill()
    {
        this.currentBill = calculateBill();
        try 
        {            
            writeBill();
                      
            if (this instanceof TillMenu)
            {
                this.setVisible(false);
                con.close();
            } // if      
            else
            {
                this.dispose();
            }
        } // try 
        catch (IOException | SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
    } // printBill
    
    private void voidItem()
    {
        VoidItemsDialog vItem = new VoidItemsDialog(this, true, new Pair<>(oldTab, newTab));
        
        Pair<Tab, Tab> result = vItem.startDialog();
        oldTab = result.getFirst();
        newTab = result.getSecond();
        
        this.setTotal();
        this.outputTextPane.setText(oldTab.toString() + newTab.toString());
    }
    
    private void voidLastItem()
    {
        if (newTab.getItems().isEmpty())
            return;       
        newTab.removeItem(newTab.getItems().get(newTab.getItems().size() - 1));
        this.outputTextPane.setText(oldTab.toString() + newTab.toString());
        this.setTotal();
    }
    
    public void dealWithButtons(Object source) throws SQLException
    {
        JButton button = (JButton)source;
        switch(button.getText())
        {
            case "Send Order":  sendOrder();  this.dispose(); break;
            case "Print Bill":  printBill(); break;
            case "Void": voidItem(); break;
            case "Void Last Item": voidLastItem(); break;
            default: break;
        } // switch 
    } // dealWithButtons()

    @Override
    public void actionPerformed(ActionEvent ae) 
    {        
        if (ae.getSource() instanceof JButton)
        {
            try { dealWithButtons(ae.getSource());} 
            catch (SQLException ex) {Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);}
        } // if
  
    } // actionPerformed
    
    public MenuCardPanel getKitchenBarMsgPanel()
    {
        return kitchenBarMsgPanel;
    }
    
    public Tab getTab()
    {
        return oldTab;
        
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
    public Connection con = null;    

    /**
     * Adds the functional features to the main panel
     * @param panel
     * @param the main panel in the set of panels
     * @return the main panel with the extra buttons added to it
     */
    protected final MenuCardPanel initialiseMainCard(MenuCardPanel panel)
    {      
        // creates the panel that everything will be created on
        JPanel BillHandlePanel = new JPanel();
        
        BillHandlePanel.setBorder(new javax.swing.border.MatteBorder(null));
        BillHandlePanel.setFocusable(false);
        BillHandlePanel.setRequestFocusEnabled(false);
        // we just want one column, zero rows implies as many coulmns as we like
        GridLayout layout = new java.awt.GridLayout(0, 1);
        System.out.println(layout);
        BillHandlePanel.setLayout(layout);

        for (String s: getOptionNames())
        {
            JButton newButton = new JButton(); 
            newButton.setText(s);
            newButton.addActionListener(this);
            BillHandlePanel.add(newButton);
            buttons.add(newButton);           
        } // for
        
        System.out.println(layout);
        panel.add(BillHandlePanel);
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
                MenuCardPanel panel = MenuCardPanel.createMenuCardPanel(this);
                panel.setName(results.getString(1));
                cardPanelsList.add(panel);
                
                // ADD the kitchen message card option to EVERY card's children
                panel.addChildCardButton(MenuCardLinkJButton.createMenuCardLinkButton(kitchenBarMsgPanel, "Kitchen/Bar Message", this));
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
                            results.getString(3), this));
                
                count++;
            }  while (results.next());
            
            // adds all c
            for (MenuCardPanel c1 : cardPanelsList)
                c1.addAllChildCardButtonsToPanel();
            
                
            
            // FIND ALL BUTTONS FOR EACH PANEL
            for (MenuCardPanel c : cardPanelsList )
            {
                query = "SELECT `NAME`, `3YP_ITEMS`.`ID`, `PRICE`, "
                    + "`FOOD_OR_DRINK`, `NEED_AGE_CHECK`, `STOCK_COUNT_ON` "
                    + "FROM `3YP_ITEMS` " 
                    + "LEFT JOIN `3YP_POS_IN_MENU` ON "
                    + "`3YP_ITEMS`.`ID` = `3YP_POS_IN_MENU`.`ID` "
                    + "WHERE `3YP_POS_IN_MENU`.`LOCATION` = \"" 
                    + c.getName() + "\" ";
                
                numberOfButtonsQuery = con.prepareStatement(query);
                numberOfButtonsQuery.executeQuery();
                results = numberOfButtonsQuery.getResultSet();
                
                while(results.next())
                {
                    MenuItemJButton newButton = MenuItemJButton.
                        createMenuItemJButton(results.getString(1), 
                            results.getInt(2), results.getDouble(3), 
                            results.getString(4), c, this, 
                            results.getBoolean(5), results.getBoolean(6));
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


                if (currentCard.getName().equals("kitchenBarPanel") && newTab.getNumberOfItems() > 0)
                {
                    
                    // detects to see if a number has been pressed and if so removes it
                    String currentTab1 = outputTextPane.getText();
                    String[] array = currentTab1.split("\n");
    
                    //if (!isNumeric(array[array.length-1]))
                      //  this.newItems.get(newItems.size()-1).setMessage(array[array.length-1]);            
                }
                else
                {
                    // detects to see if a number has been pressed and if so removes it
                    String currentTab1 = outputTextPane.getText();
                    String[] array = currentTab1.split("\n");
                    
                    if (!array[array.length-1].equals(""))
                    {
                        /* 
                        ADD CODE TO REMOVE TEXT ON THE LAST WRITTEN LINE
                        */
                    }
                }
            
            
            //MyClient.debugGUI.addText("current panel " + this.currentCard.getName());
            int parentIndex = cardPanelsList.indexOf(this.currentCard.getParentPanel());
            CardLayout cl = (CardLayout)(CardPanel.getLayout());
            cl.show(CardPanel, cardPanelsList.get(parentIndex).getName() );
            currentCard = cardPanelsList.get(parentIndex);
            

            
            // set the new Kitchen message parent to be the new card.
            kitchenBarMsgPanel.setParentPanel(currentCard);
            
            quantitySelected = -1;
            this.quantityTextPane.setText("");
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
     * @param <T>
     * @param cParent
     * @param parent
     * @param tab
     * @param out
     * @param type
     * @return 
    */
    public static Menu makeMenu(Client cParent, JFrame parent, 
            Tab tab)
    {
        
        Menu newMenu = null;
        try 
        {
            newMenu = new Menu(cParent, parent, true, tab);
            newMenu.addMouseListener(newMenu);
            newMenu.setTotal();
            newMenu.setEnabled(true);
            newMenu.setModal(true);
            newMenu.setVisible(true);

        } // try
        catch (SQLException ex) 
        {
            //Logger.getLogger(SelectTableGUI.class.getName()).log(Level.SEVERE, null, ex);
        } // catch // catch
        
        return newMenu;
    } // makeMenu
        
    private ActionListener makeKeyActionListener(final String key, Menu menu)
    {
        final Menu m = menu;
    
        ActionListener toReturn = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) 
            {      
                if (newTab.getNumberOfItems() > 0)
                {
        
                    Menu menu = m;
                    String currentText = menu.OutputArea.getText();
                    currentText += key;
                    menu.OutputArea.setText(currentText);
                    Tab newT = menu.newTab;
                    newT.getItems().get(newT.getItems().size() - 1).setMessage(key);
                    menu.messageForLatestItem = true;       
                } // if 
            } // actionPerformed
        };
        return toReturn;
        
    }
       
    public void setTotal()
    {
        double total = oldTab.getTotal();
        System.out.println("old tab total: " + oldTab.getTotal());
        System.out.println("new tab total: " + newTab.getTotal());
        total += newTab.getTotal();
        
        DecimalFormat df = new DecimalFormat("0.00");
        String totalAsString = df.format(total);
        
        System.out.println(total);
        this.totalCostArea.setText("Total: £" + totalAsString);
    }
    
    public double getTotal()
    {
        double total = oldTab.getTotal();
        total += newTab.getTotal(); 
        return total;
    } // getTotal
    
    public double getTotalDouble()
    {
        return getTotal();
    } // getTotal
    
    public static Class<?> findTypeOfParentMenu(Container cont)
    {
        int i = 1;
        while(cont.getParent() != null)
        {
            cont = cont.getParent();
            if (cont instanceof TillMenu)
                return TillMenu.class;
            if (cont instanceof Menu)
                return Menu.class;
            System.out.println("count: " + i);
                    i++;
        }
        return null;
    }
    
    public static <T extends Menu> T findParentMenu(Container cont)
    {
        while(cont.getParent() != null)
        {
            cont = cont.getParent();
            if (cont instanceof Menu)
            {
                T returnT = (T)(Menu)(Object)cont;
                return returnT;
            }    
            else if (cont instanceof TillMenu)
            {
                T returnT = (T)(TillMenu)(Object)cont;
                return returnT;
            }  
        }
        return null;
    }
    
    public Tab getOldTab()
    {
        return oldTab;
    }
    
    public void setOldTab(Tab tab)
    {
        this.oldTab = tab;
    }
    
    public Tab getNewTab()
    {
        return newTab;
    }
    
    public void setNewTab(Tab tab)
    {
        this.newTab = tab;
    }
    
} // class