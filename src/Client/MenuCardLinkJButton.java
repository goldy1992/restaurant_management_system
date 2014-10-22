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
    
    public MenuCardLinkJButton(MenuCardPanel panel)
    {
        super();    
        this.targetPanel = panel;
        this.setText(panel.getName());
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        System.out.println("child button clicked");
        
        
        if (MyClient.selectTable.menu == null)
            System.out.println("no card panel");
        CardLayout cl = (CardLayout)(MyClient.selectTable.menu.getCardPanel().getLayout());
        cl.show(MyClient.selectTable.menu.getCardPanel(), targetPanel.getName());
    }
    
}
