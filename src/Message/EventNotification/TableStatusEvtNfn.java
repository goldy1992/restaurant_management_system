/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.EventNotification;

import Server.Table;
import java.net.InetAddress;

/**
 *
 * @author Goldy
 */
public class TableStatusEvtNfn extends EventNotification 
{
    private final int tableNumber;
    private final Table.TableStatus tStatus;
    
    public TableStatusEvtNfn(InetAddress from, 
                                InetAddress to, 
                                String messageID,
                                int tableNumber,
                                Table.TableStatus t)
    {
        super(from, to, messageID);
        this.tableNumber = tableNumber;
        this.tStatus = t;
    }
    
    public int getTableNumber()
    {
        return tableNumber;
    }
    
    public Table.TableStatus getTableStatus()
    {
        return tStatus;
    }
    
}
