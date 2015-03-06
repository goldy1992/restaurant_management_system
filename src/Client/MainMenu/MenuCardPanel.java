/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Message.EventNotification.TabUpdateNfn;
import Message.EventNotification.TableStatusEvtNfn;
import Message.Message;
import static Message.Message.generateRequestID;
import Message.Table;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author mbbx9mg3
 */
public class MenuCardPanel extends JPanel
{
    private MenuCardPanel parentPanel;
    private final Menu belongsToMenu;
    private final ArrayList<MenuItemJButton> cardMenuItems;
    private final JPanel itemsPanel;    

    private final ArrayList<MenuCardLinkJButton> childCardButtons;
    private final JPanel menuSelectPanel;
    private JPanel keypadPanel;
    
    
   
    /**
     *
     * @param parentMenu
     */
    public MenuCardPanel(Menu parentMenu)
    {
        super();
        cardMenuItems = new ArrayList<>();

        childCardButtons = new ArrayList<>();
        menuSelectPanel = new JPanel();
        itemsPanel = new JPanel();      
        // true because we want to use the keyboard to control the quantity
        keypadPanel = null;
        this.belongsToMenu = parentMenu;
    }
    
    
    /**
     *
     * @param panel
     */
    public void setParentPanel(MenuCardPanel panel)
    {
        this.parentPanel = panel;
    }
    
    @Override
    public String toString()
    {
        String toReturn = "Name: " + this.getName() + "\n";
        
        if (this.parentPanel == null)
            toReturn += "Parent panel: null\n";
        else
            toReturn += "Parent panel: " + parentPanel.getName() + "\n";
        
        toReturn += "buttons(" + cardMenuItems.size() + ") : ";
        for (MenuItemJButton m : cardMenuItems)
            toReturn += m.getText() + "  ";
        toReturn +="\n";
        
        toReturn += "child panels (" + childCardButtons.size() + ") : "; 
        for (MenuCardLinkJButton m : childCardButtons)
            toReturn += m.getTargetPanel().getName() + "  ";
        toReturn += "\n";
            
        return toReturn;
       
    }
    
    /**
     * Adds a menu item button to the ArrayList of all menu items on the card  
     * @param button 
     */
    public void addMenuItemButton(MenuItemJButton button)
    {
        this.cardMenuItems.add(button);
    }
    
     /**
     * Takes the arraylist of all the menu item buttons and adds them to the
     * menu Item Panel
     * 
     * @return true if done successfully, false otherwise
     */
      public boolean addAllItemsToPanel()
    {
        if ( cardMenuItems == null || cardMenuItems.isEmpty() )
            return false;
        
        int size = cardMenuItems.size();
        
        int[] gridDimensions = Factors.closestIntSquare(size);
        itemsPanel.setLayout(new GridLayout(gridDimensions[0], gridDimensions[1]));
        
        for (MenuItemJButton b : cardMenuItems)
            itemsPanel.add(b);
        
        return true;
    }
    
    /**
     *
     * @param button
     */
    public void addChildCardButton(MenuCardLinkJButton button)
    {
        childCardButtons.add(button);
    }
    
    /**
     *
     * @return
     */
    public boolean addAllChildCardButtonsToPanel()
    {
        if ( childCardButtons == null || childCardButtons.isEmpty() )
            return false;
        
        int size = childCardButtons.size();
        
        int[] gridDimensions = Factors.closestIntSquare(size);
        menuSelectPanel.setLayout(new GridLayout(gridDimensions[0], gridDimensions[1]));
        
        for (MenuCardLinkJButton b : childCardButtons)
            menuSelectPanel.add(b);    
        
        return true;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<MenuItemJButton> getCardMenuItems()
    {
        return cardMenuItems;
    }
    
    /**
     *
     * @return
     */
    public MenuCardPanel getParentPanel()
    {
        return parentPanel;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<MenuCardLinkJButton> getChildCardButtons()
    {
        return childCardButtons;
    }
    
    /**
     *
     * @return
     */
    public JPanel getMenuSelectPanel()
    {
        return menuSelectPanel;
    }
    
    /**
     *
     * @return
     */
    public JPanel getItemsPanel()
    {
        return itemsPanel;
    }
    
    public JPanel getKeypadPanel()
    {
        return keypadPanel;
    }
    
    /**
     * This method should return true for every panel and false for the main 
     * panel.
     * 
     * @return true if the Panel has a parent, false if not.
     */
    public boolean hasParent()
    {
       return (parentPanel != null);
    }
    
    public JPanel createKeypadPanel()
    {       
        JPanel newKeypadPanel = new JPanel();
        newKeypadPanel.setLayout(new GridLayout(4,3));
        
        // makes the first 9 buttons
        for (int i= 1; i <= 10; i++)
        {
             final int x; if (i == 10)  x = 0; else x = i; // declared final so can be used in the actionlistener method
            JButton number = new JButton(x + "");
            number.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    addNumberToQuantity(x);
                } // act performed
            });
            newKeypadPanel.add(number);
        } // for
        /* Make the clear button */
        JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (belongsToMenu instanceof Menu)
                {
                    Menu menu = (Menu)belongsToMenu;
                    menu.quantitySelected = -1;
                    menu.quantityTextPane.setText("");
                }

                
                
            } // actionPerformed
        });
        newKeypadPanel.add(clear);    
        
        if (belongsToMenu.getClass() == TillMenu.class)
        {
            System.out.println("entered");
            JButton cashPay = new JButton("Cash Pay");
            cashPay.addActionListener(new ActionListener() 
            {        
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    TillMenu menu = (TillMenu)belongsToMenu;
                    double amount = menu.quantitySelected / 100.00;
                    System.out.println("current total: " + menu.getTotalDouble());
                    if (amount < menu.getTotalDouble())
                    {
                        menu.quantityTextPane.setText("Insufficient amount");
                        menu.quantitySelected = 0;                        
                    } // if
                    else
                    {
                        try 
                        {
                            double change = (amount - menu.getTotalDouble());
                            DecimalFormat df = new DecimalFormat("0.00");
                            menu.lastReceipt = menu.calculateBill();
                            menu.lastReceipt += "Amount given: \t\t" + amount + "\n";
                            menu.lastReceipt += "Change: \t\t" + change;
                            String changeString = df.format(change);
                            menu.getTill().getChangeOutputLabel().setText("£" + changeString ); 
                            menu.oldTab.mergeTabs(menu.newTab);
                            boolean x = menu.oldTab.removeAll();
                            menu.newTab.removeAll();
                            System.out.println("got here in cash off: " + x);      

                            if (menu.tabLoaded)
                            {

                                TabUpdateNfn newEvt = new TabUpdateNfn(InetAddress.getByName(
                                    menu.parentClient.client.getLocalAddress().getHostName()),
                                    InetAddress.getByName(menu.parentClient.serverAddress.getHostName()),
                                    Message.generateRequestID(), menu.oldTab);
                                menu.out.reset();
                                menu.out.writeObject(newEvt);
                                    
                                TableStatusEvtNfn newEvt1 = new TableStatusEvtNfn(InetAddress.getByName(menu.parentClient.client.getLocalAddress().getHostName()),
                                    InetAddress.getByName(menu.parentClient.serverAddress.getHostName()),
                                    generateRequestID(), menu.oldTab.getTable().getTableNumber(), Table.TableStatus.DIRTY);
               
                                menu.out.reset();
                                menu.out.writeObject(newEvt1);
                                menu.tabLoaded = false;
                            }
                            menu.dispose();
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(MenuCardPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MenuCardPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        menu.outputTextPane.setText("");
                        menu.setTotal();
                        menu.quantitySelected = -1;  
                        menu.quantityTextPane.setText("");
            
                    } // else
                } // actionPerformed
            });
            newKeypadPanel.add(cashPay);      
         
        } // if 
        
        this.keypadPanel = newKeypadPanel;
        return newKeypadPanel;
    }
    
    /**
    * A factory method to make a new Menu Card Panel
    * 
     * @param parentMenu
    * @return a new Menu Card Panel
    */
    public static MenuCardPanel createMenuCardPanel(Menu parentMenu)
    {
        MenuCardPanel x = new MenuCardPanel(parentMenu);
        x.setLayout(new GridLayout(1,0));
        x.add(x.getMenuSelectPanel());
        x.add(x.getItemsPanel());
        //x.add(x.getKeypadPanel());
        x.setFocusable(false);
        x.setRequestFocusEnabled(false);
        return x;
    }
    
    public void addNumberToQuantity(int number)
    {       
        Menu m = (Menu)belongsToMenu;
        
        Integer quantity  = m.quantitySelected;
        if (quantity <= 0)
            quantity = number;
        else
            quantity = (quantity * 10) + number;
        m.quantityTextPane.setText(quantity.toString());        
        m.quantitySelected = quantity;
        System.out.println("new quantity: " + m.quantitySelected);
    } // addNumberToQUantity
    
} // class


