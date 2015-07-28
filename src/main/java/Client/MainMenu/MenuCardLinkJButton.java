/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Client.MainMenu.Menu;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author mbbx9mg3
 */
public class MenuCardLinkJButton extends JButton implements ActionListener
{
    private MenuCardPanel targetPanel;
    private final Menu parentMenu;
    
    /**
     *
     * @param panel
     */
    private MenuCardLinkJButton(MenuCardPanel panel, String buttonText, Menu parentMenu)
    {
        super();    
        this.targetPanel = panel;
        this.setText(buttonText);
        this.parentMenu = parentMenu;
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {  
        MenuCardPanel kitchenBarMessage = parentMenu.getKitchenBarMsgPanel();
            
        // show the selected card.
        CardLayout cl = (CardLayout)(parentMenu.getCardPanel().getLayout());
        cl.show(parentMenu.getCardPanel(), targetPanel.getName());
        parentMenu.currentCard = targetPanel;
        
        // set the new parent for the kitchen bar message panel
        if (!parentMenu.currentCard.equals(kitchenBarMessage))
        {
                    System.out.println("called button");
            kitchenBarMessage.setParentPanel(targetPanel);
        }
    } // actionPerformed
    
    /**
     *
     * @return
     */
    public MenuCardPanel getTargetPanel()
    {
        return targetPanel;
    }
    
    /**
     * Set the new target panel to go to when the button is clicked.
     */
    public void setTargetPanel(MenuCardPanel x)
    {
        targetPanel = x;
    }
    
    public static MenuCardLinkJButton createMenuCardLinkButton(MenuCardPanel parent,
                                                                String buttonText,
                                                            Menu parentPanel)
    {
        MenuCardLinkJButton x = new MenuCardLinkJButton(parent, buttonText, parentPanel);
        x.addActionListener(x);
        return x;
    }
    
}
