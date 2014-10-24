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
        CardLayout cl = (CardLayout)(MyClient.selectTable.menu.getCardPanel().getLayout());
        cl.show(MyClient.selectTable.menu.getCardPanel(), targetPanel.getName());
        MyClient.selectTable.menu.currentCard= targetPanel;
    }
    
    /**
     *
     * @return
     */
    public MenuCardPanel getTargetPanel()
    {
        return targetPanel;
    }
    
    public static MenuCardLinkJButton createMenuCardLinkButton(MenuCardPanel parent,
                                                                String buttonText)
    {
        MenuCardLinkJButton x = new MenuCardLinkJButton(parent, buttonText);
        x.addActionListener(x);
        return x;
    }
    
}
