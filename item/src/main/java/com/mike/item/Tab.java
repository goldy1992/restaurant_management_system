/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.item;

import static com.mike.item.Item.Type.DRINK;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author mbbx9mg3
 */
public class Tab implements Serializable, Cloneable
{
    private int tabNumber;
    private final ArrayList<Item> allItems;
    private final ArrayList<Item> drinks;
    private final ArrayList<Item> food;
    private double total;
    
    public Tab()
    {
        this.allItems = new ArrayList<>();
        this.drinks = new ArrayList<>();
        this.food = new ArrayList<>();
        this.total = 0;
    } // tab
    
    public Tab(Tab t)
    {
        this.tabNumber = t.tabNumber;
        this.allItems = new ArrayList<>();
        for (Item i : t.allItems) {
            this.allItems.add(new Item(i));
        }
        this.drinks = new ArrayList<>();
        for (Item i : t.drinks) {
            this.drinks.add(new Item(i));
        }
        this.food = new ArrayList<>();
        for (Item i : t.food) {
            this.food.add(new Item(i)); 
        }
        this.total = t.total;
    } // tab
    
    @Override
    public String toString()
    {
        String sToReturn = "";
        for (Item i : allItems)
            sToReturn += i.outputToScreen();
        return sToReturn;
    } // toString
    
    public void addItem(Item newItem)
    {
        allItems.add(newItem);
        total += newItem.getTotalPrice();
      
        if (newItem.getType() == DRINK)
            drinks.add(allItems.get(allItems.size() - 1));
        else
            food.add(allItems.get(allItems.size() - 1));
        
    } // addItem
    
    public boolean removeItem(Item newItem)
    {
        boolean removed = false;
        
        boolean removedFromAllItems = false;        
        for (int i = 0; i < allItems.size(); i++)
            if (allItems.get(i).compareTo(newItem) == 0)
            {
                allItems.remove(i);
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
        
        total = calculateTotal();
        
        return removed;
    } // addItem
    
    public boolean removeAll()
    {
        allItems.removeAll(allItems);
        drinks.removeAll(drinks);
        food.removeAll(food);
        
        this.total = calculateTotal();
        System.out.println(allItems.isEmpty() + " " +
               food.isEmpty() + " " +
               drinks.isEmpty());
        
        return allItems.isEmpty() &&
               food.isEmpty() &&
               drinks.isEmpty();

    }
    
    public double calculateTotal()
    {
        double total = 0;
        
        for (Item i : allItems)
            total += i.getTotalPrice();
        
        return total;
                
    } // calc Total
    
    public Tab mergeTabs(Tab otherTab)
    {
        for (Item i : otherTab.getItems())
            addItem(i);
        
        return this;
    }
    
    
    public int getTable()
    {
        return getTabNumber();
    }
    
    public double getTotal()
    {
        System.out.println(total + ": " + total);
        return total;
    }
    
    public int getParent()
    {
        return getTabNumber();
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
        return allItems.size();
    }
    
    public ArrayList<Item> getItems()
    {
        return allItems;
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

    /**
     * @return the tabNumber
     */
    public int getTabNumber() {
        return tabNumber;
    }

    /**
     * @param tabNumber the tabNumber to set
     */
    public void setTabNumber(int tabNumber) {
        this.tabNumber = tabNumber;
    }
    
    
} // class