package com.mike.message.EventNotification;

import com.mike.message.Table;

/**
 *
 * @author Goldy
 */
public class TableStatusEvtNfn extends EventNotification  {

    private final int tableNumber;
    private final Table.TableStatus tStatus;
    
    /**
     * @param tableNumber
     * @param t
     */
    public TableStatusEvtNfn(int tableNumber, Table.TableStatus t) {
        super();
        this.tableNumber = tableNumber;
        this.tStatus = t;
    }

    @Override
    public String toString() { return "tableNumber: " + tableNumber + ", status: " + tStatus; }
    
    /**
     *
     * @return tableNumber
     */
    public int getTableNumber()  { return tableNumber; }
    
    /**
     * @return tableStatus
     */
    public Table.TableStatus getTableStatus()  { return tStatus; }
} // class
