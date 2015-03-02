/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import static Item.Item.Type.DRINK;
import Message.Table;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author mbbx9mg3
 */
public class Tab implements Serializable, Cloneable
{
    private final Table parent;
    private final ArrayList<Item> allitems;
    private final ArrayList<Item> drinks;
    private final ArrayList<Item> food;
    private double total;
    
    public Tab(Table parent)
    {
        this.parent = parent;
        this.allitems = new ArrayList<>();
        this.drinks = new ArrayList<>();
        this.food = new ArrayList<>();
        this.total = 0;
    } // tab
    
    public Tab(Tab t)
    {
        this.parent = t.parent;
        this.allitems = new ArrayList<>();
        for (Item i : t.allitems)
            this.allitems.add(new Item(i));
        this.drinks = new ArrayList<>();
        for (Item i : t.drinks)
            this.drinks.add(new Item(i));
        this.food = new ArrayList<>();
        for (Item i : t.food)
            this.food.add(new Item(i));
        this.total = t.total;
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
        total += newItem.getTotalPrice();
      
        if (newItem.getType() == DRINK)
            drinks.add(allitems.get(allitems.size() - 1));
        else
            food.add(allitems.get(allitems.size() - 1));
        
    } // addItem
    
    public boolean removeItem(Item newItem)
    {
        boolean removed = false;
        
        boolean removedFromAllItems = false;        
        for (int i = 0; i < allitems.size(); i++)
            if (allitems.get(i).compareTo(newItem) == 0)
            {
                allitems.remove(i);
                removedFromAllItems = true;
            } // if
        
        boolean removedFromForDItems = false;
        
        switch(newItem.type)
        {
            case FOOD:
                for (int i = 0; i < food.size(); i++)
                    if (food.get(i).compareTo(newItem) == 0)
                    {
                        food.remove(i);
                        removedFromForDItems = true;
                    } // if
                break;
            case DRINK:
                for (int i = 0; i < drinks.size(); i++)
                    if (drinks.get(i).compareTo(newItem) == 0)
                    {
                        drinks.remove(i);
                        removedFromForDItems = true;
                    } // if
                break;
        }
        
        if (removedFromForDItems && removedFromAllItems)
            removed = true;
        
        return removed;
    } // addItem
    
    public boolean removeAll()
    {
        allitems.removeAll(allitems);
        drinks.removeAll(drinks);
        food.removeAll(food);
        
        this.total = calculateTotal();
        System.out.println(allitems.isEmpty() + " " +
               food.isEmpty() + " " +
               drinks.isEmpty());
        
        return allitems.isEmpty() &&
               food.isEmpty() &&
               drinks.isEmpty();

    }
    
    public double calculateTotal()
    {
        double total = 0;
        
        for (Item i : allitems)
            total += i.getTotalPrice();
        
        return total;
                
    } // calc Total
    
    public Tab mergeTabs(Tab otherTab)
    {
        for (Item i : otherTab.getItems())
            addItem(i);
        
        return this;
    }
    
    
    public Table getTable()
    {
        return parent;
    }
    
    public double getTotal()
    {
        System.out.println(total + ": " + total);
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
    
    public static Tab cloneTab(Tab tab) throws IOException, ClassNotFoundException
    {
        Tab newTab = null;       
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectInputStream in1;
        
        try (ObjectOutputStream o = new ObjectOutputStream(baos)) {
            o.writeObject(tab);
            o.reset();
            ByteArrayInputStream baos1 = new ByteArrayInputStream(baos.toByteArray());
            in1 = new ObjectInputStream(baos1);

            newTab = (Tab) in1.readObject();
        }
        in1.close();

        return newTab;
                 
    }
    
    @Override
    public Tab clone() {
            try {
                    return (Tab) super.clone();
            } catch (CloneNotSupportedException e) {
                    return null;
            }
    }
    
    
} // class
