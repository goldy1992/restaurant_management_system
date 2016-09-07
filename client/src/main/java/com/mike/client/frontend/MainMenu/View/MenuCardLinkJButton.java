/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.frontend.MainMenu.View;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author mbbx9mg3
 */
public class MenuCardLinkJButton extends JButton implements ActionListener
{
    private MenuCardPanel targetPanel;
    private final MenuView parentMenuView;
    
    /**
     *
     * @param panel
     */
    private MenuCardLinkJButton(MenuCardPanel panel, String buttonText, MenuView parentMenuView)
    {
        super();    
        this.targetPanel = panel;
        this.setText(buttonText);
        this.parentMenuView = parentMenuView;
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {  
        MenuCardPanel kitchenBarMessage = parentMenuView.getKitchenBarMsgPanel();
            
        // show the selected card.
        CardLayout cl = (CardLayout)(parentMenuView.getCardPanel().getLayout());
        cl.show(parentMenuView.getCardPanel(), targetPanel.getName());
        parentMenuView.currentCard = targetPanel;
        
        // set the new parent for the kitchen bar message panel
        if (!parentMenuView.currentCard.equals(kitchenBarMessage))
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
                                                            MenuView parentPanel)
    {
        MenuCardLinkJButton x = new MenuCardLinkJButton(parent, buttonText, parentPanel);
        x.addActionListener(x);
        return x;
    }
    
}
