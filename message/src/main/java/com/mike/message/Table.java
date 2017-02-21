/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mike.message;

import com.mike.item.Tab;

import java.io.Serializable;

/**
 *
 * @author Goldy
 * 
 * The class to represent a table in the restaurant
 */
public class Table implements Serializable {

    /**
     * @param tableNumber the tableNumber to set
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        this.currentTab.setTabNumber(tableNumber);
    }

    /**
     * @param currentTab the currentTab to set
     */
    public void setCurrentTab(Tab currentTab) {
        this.currentTab = currentTab;
    }

    public static enum TableStatus {
        FREE,
        OCCUPIED,
        IN_USE,
        DIRTY
    }
    /**
     * the table number
     */
    private int tableNumber;
    private TableStatus tableStatus;   
    private Tab currentTab;
    
    /**
     *
     * @param tableNumber
     */
    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.tableStatus = TableStatus.FREE;
		this.currentTab = new Tab(tableNumber);
    } // constructor
    
    /**
     * @return
     */
    public int getTableNumber()
    {
        return tableNumber;
    } // getTableNumber
    
    /**
     *
     * @return
     */
    public TableStatus getStatus()
    {
        return tableStatus;
    }
    
    /**
     * 
     * @return the tab linked with the table
     */
    public Tab getCurrentTab()
    {
        return currentTab;
    } // getCurrentTab
    
    /**
     *
     * @param status
     */
    public void setTableStatus(TableStatus status) {
        tableStatus = status;
        if (status == TableStatus.DIRTY) {
            this.currentTab = new Tab(tableNumber);
        }
    }
    
    public void updateTab(Tab tab) {
        this.setCurrentTab(tab);
    }
}
