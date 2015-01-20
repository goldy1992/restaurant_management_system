/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

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
    
    /**
     *
     * @param panel
     */
    private MenuCardLinkJButton(MenuCardPanel panel, String buttonText)
    {
        super();    
        this.targetPanel = panel;
        this.setText(buttonText);
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {  

        
        Menu menu = MyClient.selectTable.menu;
        MenuCardPanel kitchenBarMessage = menu.getKitchenBarMsgPanel();
        
        
        // show the selected card.
        CardLayout cl = (CardLayout)(menu.getCardPanel().getLayout());
        cl.show(menu.getCardPanel(), targetPanel.getName());
        menu.currentCard = targetPanel;
        
        // set the new parent for the kitchen bar message panel
        if (!menu.currentCard.equals(kitchenBarMessage))
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
                                                                String buttonText)
    {
        MenuCardLinkJButton x = new MenuCardLinkJButton(parent, buttonText);
        x.addActionListener(x);
        return x;
    }
    
}
