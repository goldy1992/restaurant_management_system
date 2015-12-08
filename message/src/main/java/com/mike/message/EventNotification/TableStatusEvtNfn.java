/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.EventNotification;

import com.mike.message.Message;
import com.mike.message.Table;
import java.net.InetAddress;
import org.springframework.messaging.MessageHeaders;

/**
 *
 * @author Goldy
 */
public class TableStatusEvtNfn extends EventNotification 
{
    private final int tableNumber;
    private final Table.TableStatus tStatus;
    
    /**
     *
     * @param from
     * @param to
     * @param tableNumber
     * @param t
     */
    public TableStatusEvtNfn(int tableNumber, Table.TableStatus t)
    {
        super();
        this.tableNumber = tableNumber;
        this.tStatus = t;
    }
    
    /**
     *
     * @return
     */
    public int getTableNumber()
    {
        return tableNumber;
    }
    
    /**
     *
     * @return
     */
    public Table.TableStatus getTableStatus()
    {
        return tStatus;
    }

    
}
