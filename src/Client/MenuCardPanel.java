/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author mbbx9mg3
 */
public class MenuCardPanel extends JPanel
{
    private MenuCardPanel parentPanel;
    
    private final ArrayList<MenuItemJButton> cardMenuItems;
    private JPanel itemsPanel;    

    private final ArrayList<MenuCardLinkJButton> childCardButtons;
    private JPanel menuSelectPanel;
    
    public MenuCardPanel()
    {
        super();
        cardMenuItems = new ArrayList<MenuItemJButton>();

        childCardButtons = new ArrayList<MenuCardLinkJButton>();
        menuSelectPanel = new JPanel();
        itemsPanel = new JPanel();            
    }
    
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
    
      
      
    public void addChildCardButton(MenuCardLinkJButton button)
    {
        childCardButtons.add(button);
    }
    
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
    
    
    
    public ArrayList<MenuItemJButton> getCardMenuItems()
    {
        return cardMenuItems;
    }
    
    public MenuCardPanel getParentPanel()
    {
        return parentPanel;
    }
    
    public ArrayList<MenuCardLinkJButton> getChildCardButtons()
    {
        return childCardButtons;
    }
    
    public JPanel getMenuSelectPanel()
    {
        return menuSelectPanel;
    }
    
    public JPanel getItemsPanel()
    {
        return itemsPanel;
    }
    
    public boolean hasParent()
    {
       return (parentPanel != null);
    }
    
}


