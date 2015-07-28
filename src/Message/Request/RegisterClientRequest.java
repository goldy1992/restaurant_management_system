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
public class RegisterClientRequest extends Request
{
    public static enum ClientType
    {
        /**
         *  Request registers the bar client.
         */
        BAR,
        
        /**
         *  Request registers the kitchen client.
         */
        KITCHEN,
        
        /**
         *  Request registers the waiter client.
         */
        WAITER,
        
        /**
         *  Request registers the till client.
         */
        TILL
    } // register
    
    private ClientType clientType;
    
    /**
     *
     * @param from
     * @param to
     * @param clientType
     */
    public RegisterClientRequest(InetAddress from, 
                                InetAddress to, 
                                ClientType clientType)
    {
        super(from, to);
        this.clientType = clientType;
    } // constructor
    
    @Override
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: " + clientType;
        x+= "\n";
        
        return x;
    } // to String
    
    public ClientType getClientType()
    {
        return clientType;
    }
    
}
