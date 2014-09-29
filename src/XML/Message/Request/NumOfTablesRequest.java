/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XML.Message.Request;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */
public class NumOfTablesRequest extends Request
{   
    
    public NumOfTablesRequest(InetAddress from, 
                              InetAddress to, 
                              String messageID, 
                              Request.RequestType type)
    {
        super(from, to, messageID, type);
    } // constructor
    

    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: number of Tables";
        x+= "\n";
        
        return x;
    }
    
    
}
