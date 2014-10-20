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
            toReturn += "Parent panel: null";
        else
            toReturn += "Parent panel: " + parentPanel.getName();
        
        toReturn += "buttons: " + menuButtons + "\n";
        toReturn += "child panels: " + childrenCards + "\n";
            
        return toReturn;
       
    }
}
