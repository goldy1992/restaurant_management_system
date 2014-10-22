/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.GridLayout;
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
    private final ArrayList<MenuItemJButton> cardMenuItems;
    private final ArrayList<MenuCardPanel> childCards;
    private final ArrayList<JButton> childCardButtons;
    private JPanel menuSelectPanel;
    private JPanel itemsPanel;
    
    public MenuCardPanel()
    {
        super();
        cardMenuItems = new ArrayList<MenuItemJButton>();
        childCards = new ArrayList<MenuCardPanel>();
        childCardButtons = new ArrayList<JButton>();
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
        
        toReturn += "buttons: "; 
        for (MenuItemJButton m : cardMenuItems)
            toReturn += m.getText() + "  ";
        toReturn +="\n";
        
        toReturn += "child panels: "; 
        for (MenuCardPanel m : childCards)
            toReturn += m.getName() + "  ";
        toReturn += "\n";
            
        return toReturn;
       
    }
    
    public void addMenuButton(MenuItemJButton button)
    {
        this.cardMenuItems.add(button);
    }
    
    public void addChildCardPanel(MenuCardPanel panel)
    {
        this.childCards.add(panel);
    }
    
    public ArrayList<MenuItemJButton> getCardMenuItems()
    {
        return cardMenuItems;
    }
    
    public ArrayList<MenuCardPanel> getChildCards()
    {
        return childCards;
    }
    
    public MenuCardPanel getParentPanel()
    {
        return parentPanel;
    }
    
    public ArrayList<JButton> getChildCardButtons()
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
}
