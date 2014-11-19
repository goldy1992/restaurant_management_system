/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.EventNotification;

import Item.*;
import Item.Item.Type;
import Server.Table;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author mbbx9mg3
 */
public class NewItemNfn extends EventNotification 
{
    private final Type type;
    private final ArrayList<Item> items;
    private final int hours;
    private final int minutes;
    private final Table table;
    
    /**
     * @param from
     * @param to
     * @param messageID
     * @param type
     * @param items
     * @param table
     */
    public NewItemNfn(InetAddress from, 
                      InetAddress to, 
                      String messageID,
                      Type type,
                      ArrayList<Item> items,
                      Table table)
    {
        super(from, to, messageID);
        this.type = type;
        this.items = items;
        this.table = table;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        System.out.println(cal.get(Calendar.HOUR_OF_DAY) + ":" 
                            + cal.get(Calendar.MINUTE));
        this.hours = cal.get(Calendar.HOUR_OF_DAY);
        this.minutes = cal.get(Calendar.MINUTE); 
    } // constructor
    
    /**
     * @return
     */
    public Type getType()
    {
        return type;
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }
    
    public int getHours()
    {
        return hours;
    }
    
    public int getMinutes()
    {
        return minutes;
    }
    
    public Table getTable()
    {
        return table;
    }
    
    
    @Override
    public String toString()
    {
        String sToReturn = "Table " + table.getTableNumber() + "\n";
        sToReturn += "Time: " + getHours() + ":" + getMinutes() + "\n";
        
        for(Item i : items)
            sToReturn += i;
        return sToReturn;
    }
}

    

