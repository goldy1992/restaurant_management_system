/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Message.Request;

import java.net.InetAddress;

/**
 *
 * @author Goldy
 */
public class NumOfTablesRequest extends Request
{   
    
    /**
     *
     * @param from
     * @param to
     * @param messageID
     * @param type
     */
    public NumOfTablesRequest(InetAddress from, 
                              InetAddress to, 
                              Request.RequestType type)
    {
        super(from, to, type);
    } // constructor
    

    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: number of Tables";
        x+= "\n";
        
        return x;
    }
    
    
}
