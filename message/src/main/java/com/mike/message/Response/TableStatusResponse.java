/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mike.message.Response;

import com.mike.message.Message;
import com.mike.message.Table;
import com.mike.message.Table.TableStatus;
import com.mike.message.Request.TableStatusRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.MessageHeaders;

/**
 *
 * @author Goldy
 */
public class TableStatusResponse extends Response
{
    
    private final Map<Integer, Table.TableStatus> tableStatuses;

    /**
     *
     * @param request
     */
    public TableStatusResponse(TableStatusRequest request, Map<Integer, Table.TableStatus> tableStatuses)
    {
        super(request);
        this.tableStatuses = tableStatuses;
    } // contructor
       
       
    @Override
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Table status\nTABLES: "; 
         for (int i = 0; i < getTableStatuses().size(); i++)
            x += (getTableStatuses().get(i) + " ");
        x+= "\n";
        
        return x;
    } // toString


	public Map<Integer, Table.TableStatus> getTableStatuses() {
		return tableStatuses;
	}
	


} // class
