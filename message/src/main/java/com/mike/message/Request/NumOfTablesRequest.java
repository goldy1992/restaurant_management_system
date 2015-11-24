/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mike.message.Request;

import com.mike.message.Message;
import java.net.InetAddress;
import org.springframework.messaging.MessageHeaders;

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
     */
    public NumOfTablesRequest(InetAddress from, 
                              InetAddress to)
    {
        super();
    } // constructor
    

    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: number of Tables";
        x+= "\n";
        
        return x;
    }

    
    
}
