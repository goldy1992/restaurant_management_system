/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.EventNotification;

import Item.Tab;
import java.net.InetAddress;

/**
 *
 * @author mbbx9mg3
 */
public class TabUpdateNfn extends EventNotification {
   

    private final Tab tab;
    
    /**
     *
     * @param from
     * @param to
     * @param messageID
     * @param t the tab
     */
    public TabUpdateNfn(InetAddress from, 
                                InetAddress to, 
                                Tab t)
    {
        super(from, to);
        this.tab = t;
    }
    
    /**
     *
     * @return
     */
    public Tab getTab()
    {
        return tab;
    }

}
