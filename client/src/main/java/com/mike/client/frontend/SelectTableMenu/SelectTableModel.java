/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.frontend.SelectTableMenu;

import com.mike.message.Table;
import com.mike.message.Table.TableStatus;

import java.util.List;

/**
 *
 * @author Mike
 */
public class SelectTableModel {
    public static final int NO_TABLE_SELECTED = -1;
    private int tableSelected = NO_TABLE_SELECTED;
    private List<Table.TableStatus> tableStatuses;
    
    public SelectTableModel(List<Table.TableStatus> tableStatuses) {
    	this.tableStatuses = tableStatuses;
    }
    
    /**
     * Mutator method to select the current table.
     * @param table the table to be selected.
     */
    public void setTableSelected(int table) {
        tableSelected = table;
    }
    
    /**
     * @return The table currently selected.
     */
    public int getTableSelected() {
        return tableSelected;
    }
    
    public int getNumberOfTables() {
    	return tableStatuses.size();
    }
    
    public void setTableStatus(int index, TableStatus t) {
        tableStatuses.set(index, t);
    }
    /**
     *
     * @param i the number of the table you want to get the status of.
     * @return The status of table "i"
     */
    public Table.TableStatus getTableStatus(int i) {
        if (i < 0 || i >= tableStatuses.size()) {
			return null;
		} else {
			return tableStatuses.get(i);
		}
    }
    
 
    
}
