/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Item.Item;
import Item.Item.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.JButton;

/**
 *
 * @author Goldy
 */
public class MenuItemJButton extends JButton implements ActionListener
{

    private final BigDecimal price; 
    private final int id;
    private final Type type;
    /**
     *
     * @param name
     */
    private MenuItemJButton(String name, int id, BigDecimal price, String type)
    {
        super(name);    
        this.price = price;
        this.id = id;
        
        switch (type)
        {
            case "FOOD":
                this.type = Type.FOOD; break;
            default:
                this.type = Type.DRINK; break;
            
        } // switch
        
    } // constructor 

    @Override
    public void actionPerformed(ActionEvent ae) 
    {

        System.out.println("name: " + this.getText() + ", price: " + this.price 
                        + ", id: " + this.id + ", type: " + this.type + "\n");
        String currentText = MyClient.selectTable.menu.outputTextPane.getText();
        
        Item newItem = new Item(this.id, this.getText(), this.price, this.type );
        
        // CODE TO ADD TO TAB SHOULD BE PUT HERE
        
        
        // CODE TO ADD SCREEN
        currentText += this.getName() + "\n";
        MyClient.selectTable.menu.outputTextPane.setText(currentText);
    }
    
    public static MenuItemJButton createMenuItemJButton(String text, int id, BigDecimal price, String type)
    {
        MenuItemJButton x = new MenuItemJButton(text, id, price, type);
        x.addActionListener(x);
        return x;
    }
}
