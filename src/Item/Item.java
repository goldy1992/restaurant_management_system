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
public class Item implements Serializable, Comparable 
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
    private final BigDecimal pricePerItem;
    private final int quantity;
    private final BigDecimal totalPrice;    
    
    
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
        this.message = "";
        this.name = name;
        this.pricePerItem = price;
        this.type = type;
        this.quantity = quantity;
        BigDecimal quantityBD = new BigDecimal(quantity);
        this.totalPrice = pricePerItem.multiply(quantityBD);
    } // item
    
    public void setMessage(String message)
    {
        this.message += message;
    } // setMessage
    
    public String outputToScreen()
    {
        String stringToReturn = "";
        DecimalFormat df = new DecimalFormat("0.00");
        
        stringToReturn += this.getQuantity() + "\t" + this.getName() 
            + "\t\t\t£" + df.format(this.getTotalPrice().doubleValue()) + "\n";
            
        if (!this.getMessage().equals(""))
            stringToReturn += this.getMessage() + "\n";
        
        return stringToReturn;
    } // outputToScreen
    
    @Override
    public String toString()
    {
        return "Food\nName: " + name + 
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
    public BigDecimal getPricePerItem()
    {
        return pricePerItem;
    }
    
    public BigDecimal getTotalPrice()
    {
        return totalPrice;
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
    

    @Override
    public int compareTo(Object obj)
    {
        if ((obj instanceof Item) == false )
            return -1;
   
        Item compareItem = (Item) obj;
        if (this.id == compareItem.id &&
            this.message.equals(compareItem.message) &&
            this.name.equals(compareItem.name) &&
            this.pricePerItem.equals(compareItem.pricePerItem) &&
            this.quantity == compareItem.quantity &&
            this.totalPrice.equals(compareItem.totalPrice) &&
            this.type == compareItem.type)
            return 0;
        
        return -1;
    }
    

          
} // item
