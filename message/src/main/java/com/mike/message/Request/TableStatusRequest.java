/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mike.message.Request;

import com.mike.message.Message;
import java.net.InetAddress;
import java.util.ArrayList;
import org.springframework.messaging.MessageHeaders;

/**
 *
 * @author Goldy
 */
public class TableStatusRequest extends Request
{
    private final ArrayList<Integer> tableList;
    
    /**
     *
     * @param from
     * @param to
     * @param tableList
     */
    public TableStatusRequest(InetAddress from, 
                              InetAddress to, 
                              ArrayList<Integer> tableList)
    {
        super();
        this.tableList = tableList;
    } // constructor
    
    public static final int ALL = -1;
    public static final int SELECTION = -2;
    
    /**
     *
     * @return
     */
    @Override
    public ArrayList<Integer> getTableList()
    {
        return tableList;
    }
    
    public Integer getFirstValue()
    {
        return tableList.get(0);
    }
    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Table status\nTABLES: "; 
         for (int i = 0; i < tableList.size(); i++)
            x += (tableList.get(i) + " ");
        x+= "\n";
        
        return x;
    }

    
    
}
