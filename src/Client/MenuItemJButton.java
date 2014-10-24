/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Goldy
 */
public class MenuItemJButton extends JButton implements ActionListener
{

    /**
     *
     * @param name
     */
    private MenuItemJButton(String name)
    {
        super(name);    
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        System.out.println("menu item action performed callws");
        System.out.println(this.getText());
        String currentText = MyClient.selectTable.menu.outputTextPane.getText();
        
        // CODE TO ADD TO TAB SHOULD BE PUT HERE
        
        
        // CODE TO ADD SCREEN
        currentText += this.getName() + "\n";
        MyClient.selectTable.menu.outputTextPane.setText(currentText);
    }
    
    public static MenuItemJButton createMenuItemJButton(String text)
    {
        MenuItemJButton x = new MenuItemJButton(text);
        x.addActionListener(x);
        return x;
    }
}
