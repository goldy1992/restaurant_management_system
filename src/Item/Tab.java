/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import static Item.Item.Type.DRINK;
import Message.Table;
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
    private final ArrayList<Item> allitems;
    private final ArrayList<Item> drinks;
    private final ArrayList<Item> food;
    private BigDecimal total;
    
    public Tab(Table parent)
    {
        this.parent = parent;
        this.allitems = new ArrayList<>();
        this.drinks = new ArrayList<>();
        this.food = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    } // tab
    
    @Override
    public String toString()
    {
        String sToReturn = "";
        for (Item i : allitems)
            sToReturn += i.outputToScreen();
        return sToReturn;
    } // toString
    
    public void addItem(Item newItem)
    {
        allitems.add(newItem);
        total = total.add(newItem.getTotalPrice());
      
        if (newItem.getType() == DRINK)
            drinks.add(allitems.get(allitems.size() - 1));
        else
            food.add(allitems.get(allitems.size() - 1));
        
    } // addItem
    
    public Tab mergeTabs(Tab otherTab)
    {
        
        /*
        MAKE THIS METHOD
        */
        for (Item i : otherTab.getItems())
            addItem(i);
        
        return this;
    }
    
    
    public Table getTable()
    {
        return parent;
    }
    
    public BigDecimal getTotal()
    {
        return total;
    }
    
    public Table getParent()
    {
        return parent;
    }
    
    public ArrayList<Item> getDrinks()
    {
        return drinks;
    }
    
    public ArrayList<Item> getFood()
    {
        return food;
    }
    
    
    public int getNumberOfItems()
    {
        return allitems.size();
    }
    
    public ArrayList<Item> getItems()
    {
        return allitems;
    }
    
} // class
