/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Server.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author mbbx9mg3
 */
public class Tab implements Serializable  
{
    private final Table parent;
    private ArrayList<Item> items;
    private BigDecimal total;
    
    public Tab(Table parent)
    {
        this.parent = parent;
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    } // tab
    
    @Override
    public String toString()
    {
        String sToReturn = "";
        for (Item i : items)
            sToReturn += i.outputToScreen();
        return sToReturn;
    } // toString
    
    public void addItem(Item newItem)
    {
        items.add(newItem);
        total.add(newItem.getPrice());
    }
    
    public Table getTable()
    {
        return parent;
    }
    
} // class
