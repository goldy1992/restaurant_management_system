/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author mbbx9mg3
 */
public class Item implements Serializable 
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
    private String message;
    private final String name;
    private final BigDecimal price;
    private final int quantity;
    
    
    
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
    
    public void setMessage(String message)
    {
        this.message += message;
    } // setMessage
    
    public String outputToScreen()
    {
        String stringToReturn = "";
        
        stringToReturn += this.getQuantity() + "\t" + this.getName() 
            + "\t\t\t£" + this.getPrice().doubleValue() + "\n";
            
        if (this.getMessage() != null)
            stringToReturn += this.getMessage() + "\n";
        
        return stringToReturn;
    } // outputToScreen
    
    @Override
    public String toString()
    {
        return "Food\nName: " + name + 
                "\nid: " + id + 
                "\nType " + type +
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
