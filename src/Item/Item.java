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

    /**
     *
     */
    public static enum Type 
    {

        /**
         *
         */
        DRINK,

        /**
         *
         */
        FOOD
    }
    
    /**
     *
     */
    public Type type;
    private final int id;
    private final String message;
    private final String name;
    private final BigDecimal price;
    private final int quantity;
    
    /**
     *
     * @param id
     * @param name
     * @param message
     * @param price
     * @param type
     */
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
    
    /**
     *
     * @param id
     * @param name
     * @param price
     * @param type
     */
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
    
    /**
     *
     * @param id
     * @param name
     * @param message
     * @param price
     * @param type
     * @param quantity
     */
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
    
    /**
     *
     * @param id
     * @param name
     * @param price
     * @param type
     * @param quantity
     */
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
    
    /**
     *
     * @return
     */
    public int getID()
    {
        return id;
    }
    
    /**
     *
     * @return
     */
    public String getMessage()
    {
        return message;
    }
    
    /**
     *
     * @return
     */
    public BigDecimal getPrice()
    {
        return price;
    }
    
    /**
     *
     * @return
     */
    public int getQuantity()
    {
        return quantity;
    }
    
    /**
     *
     * @return
     */
    public String getName()
    {
        return name;
    }
    
    /**
     *
     * @return
     */
    public Type getType()
    {
        return type;
    }
    
} // item
