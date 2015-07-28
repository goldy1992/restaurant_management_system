/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Item.Item;
import Item.Item.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author Goldy
 */
public class MenuItemJButton extends JButton implements ActionListener
{

    
    /**
     * The reference to the object that stores the database connection
     */
    public Connection con = null;  
    private final Menu belongsToMenu;
    private final double price; 
    private final int id;
    private final Type type;
    private final JComponent parent;
    private final boolean needAgeCheck;
    private final boolean stockCount;
    
    /**
     *
     * @param name
     */
    private MenuItemJButton(String name, int id, 
            double price, String type, JComponent parent, 
            Menu parentMenu, boolean needAgeCheck, boolean stockCount)
    {
        super(name);    
        this.price = price;
        this.id = id;
        this.parent = parent;
        this.belongsToMenu = parentMenu;
        this.needAgeCheck = needAgeCheck;
        this.stockCount = stockCount;
        
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
        if (this.needAgeCheck && !belongsToMenu.seenID)
        {
            int number = JOptionPane.showConfirmDialog(
                this.parent, "Does the customer pass an ID Check?",
                "ID Check", JOptionPane.YES_NO_OPTION);
            
             if (number == 0) belongsToMenu.seenID = true;
             else return;
        } // if
         
        // parse the quantity
        int quantity = belongsToMenu.quantitySelected;
        if (quantity < 0)
            quantity = 1;
               
        try 
        {
            // Database connection
            
            if (this.stockCount)
            {
                //initialise the connection to the database
                con = DriverManager.getConnection(
                    "jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3", 
                    "mbbx9mg3",
                    "Fincherz+2013");

                System.out.println("id: " + id);
                // query: UPDATE `3YP_ITEMS` SET `QUANTITY` = `QUANTITY` - 1 WHERE ID = 27 
                PreparedStatement numberOfButtonsQuery = null;
                String query = "SELECT `3YP_ITEMS`.`QUANTITY` FROM `3YP_ITEMS` "
                    + "WHERE `3YP_ITEMS`.`ID` = \"" 
                    + id + "\"";

                System.out.println("Query: " + query);

                numberOfButtonsQuery = con.prepareStatement(query);
                numberOfButtonsQuery.executeQuery();
                ResultSet results = numberOfButtonsQuery.getResultSet();

                int inStock = -2;
                while(results.next())                 
                    inStock = results.getInt(1);

                con.close();
                
                System.out.println("in stock: " + inStock);
                
                if (quantity > inStock)
                {
                    JOptionPane.showMessageDialog(this.belongsToMenu, "Error: There's only " + inStock + " " + this.getText() + " available in stock");
                    belongsToMenu.quantitySelected = -1;
                    belongsToMenu.quantityTextPane.setText("");
                    return;
                } // if
                else // remove from stock
                {
                                   //initialise the connection to the database
                    con = DriverManager.getConnection(
                        "jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3", 
                        "mbbx9mg3",
                        "Fincherz+2013");

                    System.out.println("id: " + id);
                    // query: UPDATE `3YP_ITEMS` SET `QUANTITY` = `QUANTITY` - 1 WHERE ID = 27 

                    query = "UPDATE `3YP_ITEMS` "  
                        + "SET `3YP_ITEMS`.`QUANTITY` = `3YP_ITEMS`.`QUANTITY` - \"" 
                        + quantity + "\""
                        + "WHERE `3YP_ITEMS`.`ID` = \"" 
                        + id + "\"";
                    numberOfButtonsQuery = con.prepareStatement(query);
                    numberOfButtonsQuery.executeUpdate();
                    con.close();

                } // else
                    
            } // if
        } catch (SQLException ex)
        {
            Logger.getLogger(MenuItemJButton.class.getName()).log(Level.SEVERE, null, ex);
        }

        // CODE TO ADD TO TAB SHOULD BE PUT HERE
        Item newItem = new Item(this.id, this.getText(), this.price, this.type, quantity, stockCount);
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
    
    
    
    public static MenuItemJButton createMenuItemJButton(String text, 
        int id, double price, String type, JComponent parent, Menu parentMenu, 
        boolean ageCheck, boolean stockCount)
    {
        MenuItemJButton x = new MenuItemJButton(text, id, price, type, parent, 
            parentMenu, ageCheck, stockCount);
        x.addActionListener(x);
        return x;
    }
}
