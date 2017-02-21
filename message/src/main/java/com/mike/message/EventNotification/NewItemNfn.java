package com.mike.message.EventNotification;

import com.mike.item.Item;
import com.mike.item.Item.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author mbbx9mg3
 */
public class NewItemNfn extends EventNotification {

    private final Type type;
    private final ArrayList<Item> items;
    private final int hours;
    private final int minutes;
    private final int table;

    /**
     * @param type
     * @param items
     * @param table
     */
    public NewItemNfn( Type type, ArrayList<Item> items, int table) {
        super();
        this.type = type;
        this.items = items;
        this.table = table;

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        this.hours = cal.get(Calendar.HOUR_OF_DAY);
        this.minutes = cal.get(Calendar.MINUTE);
    } // constructor

    /**
     * @return
     */
    public Type getType() { return type; }
    public ArrayList<Item> getItems() { return items; }
    public int getHours()  { return hours; }
    public int getMinutes()  { return minutes; }
    public int getTable()  { return table; }

    @Override
    public String toString() {
        String sToReturn = "Table " + table + "\n";
        sToReturn += "Time: " + getHours() + ":" + getMinutes() + "\n";

        for(Item i : items) {
            sToReturn += i;
        }
        return sToReturn;
    } // toString
} // class

    

