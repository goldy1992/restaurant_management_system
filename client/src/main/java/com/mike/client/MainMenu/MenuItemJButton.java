/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.MainMenu;

import com.mike.client.MainMenu.Model.MenuItem;
import com.mike.item.Item.Type;

import javax.swing.*;
import java.sql.Connection;

/**
 *
 * @author Goldy
 */
public class MenuItemJButton extends JButton
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
    
    public MenuItemJButton(String name, int id, 
            double price, String type, JComponent parent, 
             boolean needAgeCheck, boolean stockCount)
    {
    	this(name, id, price, type, parent, null, needAgeCheck, stockCount);
    }

//    @Override
//    public void actionPerformed(ActionEvent ae)
//    {
//        if (this.isNeedAgeCheck() && !belongsToMenu.seenID)
//        {
//            int number = JOptionPane.showConfirmDialog(
//                this.parent, "Does the customer pass an ID Check?",
//                "ID Check", JOptionPane.YES_NO_OPTION);
//
//             if (number == 0) belongsToMenu.seenID = true;
//             else return;
//        } // if
//
//        // parse the quantity
//        int quantity = belongsToMenu.quantitySelected;
//        if (quantity < 0)
//            quantity = 1;
//
//        try
//        {
//            // Database connection
//
//            if (this.isStockCount())
//            {
//                //initialise the connection to the database
//
//				List<ItemDAO> response = belongsToMenu.getMod.query("FROM ItemDAO i where i.id = " + getId());
//                System.out.println("id: " + getId());
//                // query: UPDATE `3YP_ITEMS` SET `QUANTITY` = `QUANTITY` - 1 WHERE ID = 27
//                PreparedStatement numberOfButtonsQuery = null;
//                String query = "SELECT `3YP_ITEMS`.`QUANTITY` FROM `3YP_ITEMS` "
//                    + "WHERE `3YP_ITEMS`.`ID` = \""
//                    + getId() + "\"";
//
//                System.out.println("Query: " + query);
//
//                numberOfButtonsQuery = con.prepareStatement(query);
//                numberOfButtonsQuery.executeQuery();
//                ResultSet results = numberOfButtonsQuery.getResultSet();
//
//                int inStock = -2;
//                while(results.next())
//                    inStock = results.getInt(1);
//
//                con.close();
//
//                System.out.println("in stock: " + inStock);
//
//                if (quantity > inStock)
//                {
//                    JOptionPane.showMessageDialog(this.belongsToMenu, "Error: There's only " + inStock + " " + this.getText() + " available in stock");
//                    belongsToMenu.quantitySelected = -1;
//                    belongsToMenu.quantityTextPane.setText("");
//                    return;
//                } // if
//                else // remove from stock
//                {
//                                   //initialise the connection to the database
//                    con = DriverManager.getConnection(
//                        "jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3",
//                        "mbbx9mg3",
//                        "Fincherz+2013");
//
//                    System.out.println("id: " + getId());
//                    // query: UPDATE `3YP_ITEMS` SET `QUANTITY` = `QUANTITY` - 1 WHERE ID = 27
//
//                    query = "UPDATE `3YP_ITEMS` "
//                        + "SET `3YP_ITEMS`.`QUANTITY` = `3YP_ITEMS`.`QUANTITY` - \""
//                        + quantity + "\""
//                        + "WHERE `3YP_ITEMS`.`ID` = \""
//                        + getId() + "\"";
//                    numberOfButtonsQuery = con.prepareStatement(query);
//                    numberOfButtonsQuery.executeUpdate();
//                    con.close();
//
//                } // else
//
//            } // if
//        } catch (SQLException ex)
//        {
//            Logger.getLogger(MenuItemJButton.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        // CODE TO ADD TO TAB SHOULD BE PUT HERE
//        Item newItem = new Item(this.getId(), this.getText(), this.getPrice(), this.getType(), quantity, isStockCount());
//        belongsToMenu.newTab.addItem(newItem);
//
//        belongsToMenu.quantitySelected = -1;
//        belongsToMenu.quantityTextPane.setText("");
//
//
//           // MyClient.debugGUI.addText( newItem.toString() + "\n");
//        // CODE TO ADD SCREEN
//
//        String currentText = belongsToMenu.outputTextPane.getText();
//        if (belongsToMenu.messageForLatestItem)
//        belongsToMenu.outputTextPane.setText(currentText + "\n" + newItem.outputToScreen() );
//        else         belongsToMenu.outputTextPane.setText(currentText  + newItem.outputToScreen() );
//        belongsToMenu.messageForLatestItem = false;
//
//        // ADD TOTAL
//        belongsToMenu.setTotal();
//    }

	public static MenuItemJButton createMenuItemJButton(MenuItem menuItem, JComponent parent, Menu parentMenu)
	{
		MenuItemJButton x = new MenuItemJButton(menuItem.getName(), menuItem.getId(), menuItem.getPrice(), menuItem.getType(), parent,
				parentMenu, menuItem.isNeedAgeCheck(), menuItem.isStockCount());
		x.addActionListener(Menu.menuController);
		return x;
	}

	public double getPrice() {
		return price;
	}

	public int getId() {
		return id;
	}

	public Type getType() {
		return type;
	}

	public boolean isNeedAgeCheck() {
		return needAgeCheck;
	}

	public boolean isStockCount() {
		return stockCount;
	}
}
