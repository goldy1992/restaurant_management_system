/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Client.MainMenu.MenuCardLinkJButton;
import Client.MainMenu.Menu;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
     */
    private MenuCardPanel(Menu parentMenu)
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
        JPanel keypadPanel = new JPanel();
        keypadPanel.setLayout(new GridLayout(4,3));
        
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
            keypadPanel.add(number);
        } // for
        /* Make the clear button */
        JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Menu menu = belongsToMenu;
                menu.quantitySelected = -1;
                menu.quantityTextPane.setText("");
            } // actionPerformed
        });
        keypadPanel.add(clear);    
        
        if (belongsToMenu.getClass() == TillMenu.class)
        {
            System.out.println("entered");
            JButton cashPay = new JButton("Cash Pay");
            cashPay.addActionListener(new ActionListener() 
            {        
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    Menu menu = belongsToMenu;
                    menu.quantitySelected = -1;
                    menu.quantityTextPane.setText("");
                } // actionPerformed
            });
            keypadPanel.add(cashPay);      
         
        }
        this.keypadPanel = keypadPanel;
        return keypadPanel;
    }
    
    /**
    * A factory method to make a new Menu Card Panel
    * 
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
        //Menu belongsToMenu = MyClient.selectTable.menu;
        Integer quantity  = belongsToMenu.quantitySelected;
        if (quantity <= 0)
            quantity = number;
        else
            quantity = (quantity * 10) + number;
        belongsToMenu.quantityTextPane.setText(quantity.toString());
        
        belongsToMenu.quantitySelected = quantity;
        
    }
    
}


