/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import java.math.BigDecimal;

/**
 *
 * @author mbbx9mg3
 */
public class Item 
{   
    public static enum Type 
    {
        DRINK, FOOD
    }
    
    public Type type;
    private final int id;
    private final String message;
    private final String name;
    private final BigDecimal price;
    private final int quantity;
    
    
    public Item(int id, String name, 
                String message, BigDecimal price, 
                Type type)
    {
        this.id = id;
        this.message = message;
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = 1;
    } // item
    
    public Item(int id, String name, 
                BigDecimal price, Type type)
    {
        this.id = id;
        this.message = null;
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = 1;
    } // item
    
    public Item(int id, String name, String message, 
                BigDecimal price, Type type, int quantity)
    {
        this.id = id;
        this.message = message;
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    } // item
    
    public Item(int id, String name, 
                BigDecimal price, Type type, int quantity)
    {
        this.id = id;
        this.message = null;
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    } // item
    
    @Override
    public String toString()
    {
        return "Food\nName: " + name + 
                "\nid: " + id + 
                "\nprice " + price +
                "\nquantity " + quantity +
                "\nmessage " + message;
    }
    
    public int getID()
    {
        return id;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public BigDecimal getPrice()
    {
        return price;
    }
    
    public int getQuantity()
    {
        return quantity;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Type getType()
    {
        return type;
    }
    
} // item
