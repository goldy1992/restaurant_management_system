/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.EventNotification;

import Item.*;
import Item.Item.Type;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author mbbx9mg3
 */
public class NewItemNfn extends EventNotification 
{
    private final Type type;
    private final ArrayList<Item> items;
    
    /**
     * @param from
     * @param to
     * @param messageID
     * @param type
     * @param items
     */
    public NewItemNfn(InetAddress from, 
                      InetAddress to, 
                      String messageID,
                      Type type,
                      ArrayList<Item> items)
    {
        super(from, to, messageID);
        this.type = type;
        this.items = items;
    }
    
    /**
     *
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
}

    

