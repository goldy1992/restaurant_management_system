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
     * @param messageID
     * @param tableNumber
     * @param t
     */
    public TableStatusEvtNfn(InetAddress from, 
                                InetAddress to, 
                                int tableNumber,
                                Table.TableStatus t)
    {
        super(from, to);
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

    @Override
    public Message getPayload() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MessageHeaders getHeaders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
