/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.EventNotification;

import Message.Table;
import java.net.InetAddress;

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
    
}
