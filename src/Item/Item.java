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
    private int id;
    private String message;
    private String name;
    private BigDecimal price;
    
    public Item(int id, String name, String message, BigDecimal price)
    {
        this.id = id;
        this.message = message;
        this.name = name;
        this.price = price;
    } // item
    
    public Item(int id, String name, BigDecimal price)
    {
        this.id = id;
        this.message = null;
        this.name = name;
        this.price = price;
    } // item
    
    @Override
    public String toString()
    {
        return "Food\nName: " + name + 
                "\nid: " + id + 
                "\nprice " + price +
                "\nmessage " + message;
    }
    
} // item
