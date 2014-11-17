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

    private final double price; 
    private final int id;
    /**
     *
     * @param name
     */
    private MenuItemJButton(String name, int id, double price)
    {
        super(name);    
        this.price = price;
        this.id = id;
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {

        System.out.println("name: " + this.getText() + ", price: " + this.price + ", id: " + this.id + "\n");
        String currentText = MyClient.selectTable.menu.outputTextPane.getText();
        
        // CODE TO ADD TO TAB SHOULD BE PUT HERE
        
        
        // CODE TO ADD SCREEN
        currentText += this.getName() + "\n";
        MyClient.selectTable.menu.outputTextPane.setText(currentText);
    }
    
    public static MenuItemJButton createMenuItemJButton(String text, int id, double price)
    {
        MenuItemJButton x = new MenuItemJButton(text, id, price);
        x.addActionListener(x);
        return x;
    }
}
