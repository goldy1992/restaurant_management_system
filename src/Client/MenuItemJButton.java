/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Item.Item;
import Item.Item.Type;
import Item.Tab;
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
        Menu menu = MyClient.selectTable.menu;
        Tab currentTab = menu.getTab();

        // CODE TO ADD TO TAB SHOULD BE PUT HERE
        Item newItem = new Item(this.id, this.getText(), this.price, this.type );
        currentTab.addItem(newItem);
        menu.addNewItem(newItem);
        
        System.out.println(newItem);
        
        
        // CODE TO ADD SCREEN
        menu.outputTextPane.setText(currentTab.toString());
    }
    
    public static MenuItemJButton createMenuItemJButton(String text, int id, BigDecimal price, String type)
    {
        MenuItemJButton x = new MenuItemJButton(text, id, price, type);
        x.addActionListener(x);
        return x;
    }
}
