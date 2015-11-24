/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mike.message.Response;

import com.mike.message.Message;
import com.mike.message.Table;
import com.mike.message.Request.TableStatusRequest;
import java.util.ArrayList;
import org.springframework.messaging.MessageHeaders;

/**
 *
 * @author Goldy
 */
public class TableStatusResponse extends Response
{
    
    private final ArrayList<Table.TableStatus> tableStatuses;

    /**
     *
     * @param request
     */
    public TableStatusResponse(TableStatusRequest request)
    {
        super(request);
        tableStatuses = new ArrayList<Table.TableStatus>();
    } // contructor
       
    /**
     *
     * @return
     */
    public ArrayList<Table.TableStatus> getStatuses()
    {
        return tableStatuses;
    } // getTableStatuses
    
    @Override
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Table status\nTABLES: "; 
         for (int i = 0; i < tableStatuses.size(); i++)
            x += (tableStatuses.get(i) + " ");
        x+= "\n";
        
        return x;
    } // toString


} // class
