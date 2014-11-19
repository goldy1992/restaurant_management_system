/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Request;

import java.net.InetAddress;

/**
 *
 * @author mbbx9mg3
 */
public class TabRequest extends Request
{
    
   private final int tabNumber;
    /**
     * @param from
     * @param to
     * @param messageID
     * @param type
     * @param tableNumber
     */
    public TabRequest(InetAddress from, 
                              InetAddress to, 
                              String messageID, 
                              Request.RequestType type,
                              int tableNumber)
    {
        super(from, to, messageID, type);
        this.tabNumber = tableNumber;
    } // constructor
    

    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Tab Request";
        x+= "\nTab Number: " + tabNumber + "\n";
        
        return x;
    }   
    
    public int getTabNumber()
    {
        return tabNumber;
    }


    
} // class