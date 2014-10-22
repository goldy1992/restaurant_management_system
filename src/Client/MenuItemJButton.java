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
    public MenuItemJButton(String name)
    {
        super(name);    
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        System.out.println(this.getText());
        String currentText = Menu.myOutput.getText();
        
        // CODE TO ADD TO TAB SHOULD BE PUT HERE
        
        
        // CODE TO ADD SCREEN
        currentText += this.getName() + "\n";
        Menu.myOutput.setText(currentText);
    }
}
