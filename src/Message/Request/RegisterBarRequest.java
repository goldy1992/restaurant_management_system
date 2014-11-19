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
public class RegisterBarRequest extends Request
{
    /**
     *
     * @param from
     * @param to
     * @param messageID
     * @param type
     */
    public RegisterBarRequest(InetAddress from, 
                              InetAddress to, 
                              String messageID, 
                              Request.RequestType type)
    {
        super(from, to, messageID, type);
    } // constructor
    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Register bar";
        x+= "\n";
        
        return x;
    } // to String
    
}
