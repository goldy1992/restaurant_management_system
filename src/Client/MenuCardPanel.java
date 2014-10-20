/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author mbbx9mg3
 */
public class MenuCardPanel extends JPanel 
{
    private MenuCardPanel parentPanel;
    private ArrayList<MenuItemJButton> menuButtons;
    private ArrayList<MenuCardPanel> childrenCards;
    
    public MenuCardPanel()
    {
        super();
        menuButtons = new ArrayList<MenuItemJButton>();
        childrenCards = new ArrayList<MenuCardPanel>();
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
        for (MenuItemJButton m : menuButtons)
            toReturn += m.getText() + "  ";
        toReturn +="\n";
        
        toReturn += "child panels: "; 
        for (MenuCardPanel m : childrenCards)
            toReturn += m.getName() + "  ";
        toReturn += "\n";
            
        return toReturn;
       
    }
    
    public void addMenuButton(MenuItemJButton button)
    {
        this.menuButtons.add(button);
    }
    
    public void addChildCardPanel(MenuCardPanel panel)
    {
        this.childrenCards.add(panel);
    }
}
