/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Client.MainMenu.Menu;
import static Client.MainMenu.Menu.isNumeric;
import Item.Item;
import Item.Item.Type;
import Item.Tab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * @author Goldy
 */
public class MenuItemJButton extends JButton implements ActionListener
{

    private final Menu belongsToMenu;
    private final BigDecimal price; 
    private final int id;
    private final Type type;
    private final JComponent parent;
    /**
     *
     * @param name
     */
    private MenuItemJButton(String name, int id, BigDecimal price, String type, JComponent parent, Menu parentMenu)
    {
        super(name);    
        this.price = price;
        this.id = id;
        this.parent = parent;
        this.belongsToMenu = parentMenu;
        
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
        //Menu belongsToMenu = MyClient.selectTable.menu;
      
        // parse the quantity
        int quantity = belongsToMenu.quantitySelected;
        if (quantity < 0)
            quantity = 1;
        

        // CODE TO ADD TO TAB SHOULD BE PUT HERE
        Item newItem = new Item(this.id, this.getText(), this.price, this.type, quantity);
        belongsToMenu.newTab.addItem(newItem);
        
        belongsToMenu.quantitySelected = -1;
        belongsToMenu.quantityTextPane.setText("");
        

           // MyClient.debugGUI.addText( newItem.toString() + "\n");   
        // CODE TO ADD SCREEN
        
        String currentText = belongsToMenu.outputTextPane.getText();
        if (belongsToMenu.messageForLatestItem)
        belongsToMenu.outputTextPane.setText(currentText + "\n" + newItem.outputToScreen() );
        else         belongsToMenu.outputTextPane.setText(currentText  + newItem.outputToScreen() );
        belongsToMenu.messageForLatestItem = false;
        
        // ADD TOTAL
        belongsToMenu.setTotal();
    }
    
    
    
    public static MenuItemJButton createMenuItemJButton(String text, int id, BigDecimal price, String type, JComponent parent, Menu parentMenu)
    {
        MenuItemJButton x = new MenuItemJButton(text, id, price, type, parent, parentMenu);
        x.addActionListener(x);
        return x;
    }
}
