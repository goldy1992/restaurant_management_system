/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.MainMenu;

import com.mike.client.MainMenu.Model.MenuItem;
import com.mike.item.Item.Type;

import javax.swing.*;

/**
 *
 * @author Goldy
 */
public class MenuItemJButton extends JButton {

    private final double price; 
    private final int id;
    private final Type type;
    private final boolean needAgeCheck;
    private final boolean stockCount;

    private MenuItemJButton(String name, int id,
            double price, String type, JComponent parent, 
            Menu parentMenu, boolean needAgeCheck, boolean stockCount)
    {
        super(name);    
        this.price = price;
        this.id = id;
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
