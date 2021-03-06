package com.github.goldy1992.rms.item;
 
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * @author mbbx9mg3
 */
public class Item implements Serializable, Comparable, Cloneable, com.github.goldy1992.rms.item.Uniqueness {

    public static enum Type {
        DRINK,
        FOOD
    }
    
    public Type type;
    private final int id;
    private String message;
    private final String name;
    private final double pricePerItem;
    private int quantity;
    private double totalPrice;    
    public final String uniqueID;
    private final boolean stockCount;
       
    /**
     *
     * @param id
     * @param name
     * @param price
     * @param type
     * @param quantity
     */
    public Item(int id, String name, double price, Type type, int quantity, boolean stockCount) {
        this.id = id;
        this.message = "";
        this.name = name;
        this.pricePerItem = price;
        this.type = type;
        this.quantity = quantity;
        this.totalPrice = pricePerItem * quantity;
        this.uniqueID = id + name + type + message + quantity; //generateUniqueID();
        this.stockCount = stockCount;
    } // item
    
    public Item(Item i) {
        this.type = i.type;
        this.id = i.id;
        this.message = i.message;
        this.name = i.name;
        this.pricePerItem = i.pricePerItem;
        this.quantity = i.quantity;
        this.totalPrice = i.totalPrice;
        this.uniqueID = i.uniqueID;
        this.stockCount = i.isStockCount();
    }
    
    public void appendCharacter(char key) { this.message += key; } // setMessage
    
    
    public String firstLineScreenOutput() {
        String stringToReturn = "";
        DecimalFormat df = new DecimalFormat("0.00");
        
        stringToReturn += this.getQuantity() + "\t" + this.getName() 
            + "\t\t\t£" + df.format(this.getTotalPrice()) + "\n";

        return stringToReturn;       
    }
    
    public String secondLineScreenOutput() {
        String stringToReturn = "";
        if (!this.getMessage().equals("")) {
            stringToReturn += this.getMessage() + "\n";
        }
        return stringToReturn;       
    }

    public String outputToScreen() {
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

    private void setTotalPrice() {
        totalPrice = pricePerItem;
        totalPrice = totalPrice * quantity;
    }
    
    public void setQuantity(int q) {
        this.quantity = q;
        setTotalPrice();
    }

    public Integer getQuantity()  { return quantity; }
    public String getName()  { return name; }
    public Type getType() { return type; }
    public int getID() { return id; }
    public String getMessage() { return message; }
    public double getPricePerItem()  { return pricePerItem; }
    public double getTotalPrice()  { return totalPrice; }
    public boolean isStockCount() { return stockCount; }
    
    @Override
    public int compareTo(Object obj) {
        if ((obj instanceof Item) == false ) {
            return -1;
        }
   
        Item compareItem = (Item) obj;
        if (this.uniqueID.equals(compareItem.uniqueID)) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof Item) == false) {
            return false;
        }

        Item compareItem = (Item) obj;
        if (this.uniqueID.equals(compareItem.uniqueID)) {
            return true;
        }
        return false;
    }
    @Override
    public Item clone() throws CloneNotSupportedException {
        try {
                return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
                return null;
        }
    }

              
} // item