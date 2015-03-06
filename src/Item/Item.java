/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author mbbx9mg3
 */
public class Item implements Serializable, Comparable, Cloneable
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
    private final double pricePerItem;
    private int quantity;
    private double totalPrice;    
       
    /**
     *
     * @param id
     * @param name
     * @param price
     * @param type
     * @param quantity
     */
    public Item(int id, String name, 
                double price, Type type, int quantity) 
    {
        this.id = id;
        this.message = "";
        this.name = name;
        this.pricePerItem = price;
        this.type = type;
        this.quantity = quantity;
        this.totalPrice = pricePerItem * quantity;
    } // item
    
    public Item(Item i)
    {
        this.type = i.type;
        this.id = i.id;
        this.message = i.message;
        this.name = i.name;
        this.pricePerItem = i.pricePerItem;
        this.quantity = i.quantity;
        this.totalPrice = i.totalPrice;
    }
    
    public void setMessage(String message)
    {
        this.message += message;
    } // setMessage
    
    
    public String firstLineScreenOutput()
    {
        String stringToReturn = "";
        DecimalFormat df = new DecimalFormat("0.00");
        
        stringToReturn += this.getQuantity() + "\t" + this.getName() 
            + "\t\t\t£" + df.format(this.getTotalPrice()) + "\n";
            
        
        return stringToReturn;       
    }
    
    public String secondLineScreenOutput()
    {
        String stringToReturn = "";
            
        if (!this.getMessage().equals(""))
            stringToReturn += this.getMessage() + "\n";
        
        return stringToReturn;       
    }
    public String outputToScreen()
    {
        String stringToReturn = "";
        stringToReturn += this.firstLineScreenOutput();
        stringToReturn += this.secondLineScreenOutput();
        
        return stringToReturn;
    } // outputToScreen
    
    @Override
    public String toString()
    {
        return "\nName: " + name + 
                "\nid: " + id + 
                "\nType " + type +
                "\nprice " + pricePerItem +
                "\nquantity " + quantity +
                "\nmessage " + message;
    } // toString
    
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
    public double getPricePerItem()
    {
        return pricePerItem;
    }
    
    public double getTotalPrice()
    {
        return totalPrice;
    }
    
    private void setTotalPrice()
    {
        totalPrice = pricePerItem;
        totalPrice = totalPrice * quantity;
    }
    
    public void setQuantity(int q)
    {
        this.quantity = q;
        setTotalPrice();
    }
    
    /**
     *
     * @return
     */
    public Integer getQuantity()
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
    
    @Override
    public int compareTo(Object obj)
    {
        if ((obj instanceof Item) == false )
            return -1;
   
        Item compareItem = (Item) obj;
        if (this.id == compareItem.id &&
            this.message.equals(compareItem.message) &&
            this.name.equals(compareItem.name) &&
            this.pricePerItem == compareItem.pricePerItem &&
            this.quantity == compareItem.quantity &&
            this.totalPrice == compareItem.totalPrice &&
            this.type == compareItem.type)
            return 0;
        
        return -1;
    }
    
    @Override
    public Item clone() {
        try {
                return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
                return null;
        }
}   

              
} // item