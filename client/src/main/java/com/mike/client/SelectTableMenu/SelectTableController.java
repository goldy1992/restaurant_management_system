/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.SelectTableMenu;

import com.mike.client.SelectTableMenu.View.SelectTableView;
import com.mike.client.MainMenu.Menu;
import static com.mike.client.SelectTableMenu.SelectTableModel.NO_TABLE_SELECTED;

import com.mike.client.MessageSender;
import com.mike.client.WaiterClient;
import com.mike.item.Tab;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;
import com.mike.message.Table.TableStatus;
import static com.mike.message.Table.TableStatus.DIRTY;
import static com.mike.message.Table.TableStatus.IN_USE;
import static com.mike.message.Table.TableStatus.OCCUPIED;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 *
 * @author Mike
 */
@MessageEndpoint
public class SelectTableController implements ActionListener 
{
	@Autowired
	private MessageSender messageSender;
	 
    public SelectTableModel model;
    public SelectTableView view;
    private boolean initialised = false;
	
	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
		
    
    public boolean isInitialised() {
		return initialised;
	}

	public void setInitialised(boolean initialised) {
		this.initialised = initialised;
	}

	public void init(ArrayList<TableStatus> tableStatuses) {
		if (!initialised) {
			this.model = new SelectTableModel(tableStatuses);
	        this.view = new SelectTableView(this, tableStatuses);
	        view.setVisible(true); 
		} 	
    }
    
  /**
     *
     * @param index
     * @param t
     * @return 
     */
    public boolean setTableStatus(int index, TableStatus t)
    {
        if (index < 0 || index >= view.getTableButtons().length)
            return false;    
        
        model.setTableStatus(index, t);
        view.setTableStatus(index, t);
        return true;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
         
        int tableSelected = model.getTableSelected();
        if (view.isCleanTableSelected(event)) {
            TableStatus selectedTableStatus = model.getTableStatus(tableSelected);
            if (selectedTableStatus == DIRTY) {
                messageSender.sendTableStatusEventNotification(tableSelected, selectedTableStatus);
            } // if
            return;
        } // if getSource

        
        if (view.isOpenTableSelected(event)) {
            if (tableSelected == NO_TABLE_SELECTED) {
                view.getOutputLabel().setOutputLabelNoTableSelected();
            } else if (model.getTableStatus(tableSelected) == IN_USE) {
                view.getOutputLabel().setOutputLabelTableInUse(tableSelected);
            } else {
              //  parentClient.sendTableStatusEventNotification(tableSelected,  IN_USE);
            // messageSender.sendTabRequest(tableSelected);
            } // else
            return;
        } // if open table
        
        if(view.isMoveTableSelected(event)) {
         	 System.out.println("talble selected");
        	 messageSender.sendTabRequest(tableSelected);
        	 System.out.println("talble selected");
        	 return;
        } // TODO: implement move table
        
    	for (int i = 1; i <= model.getNumberOfTables(); i++) {
    		if (view.isTableXSelected(event, i)) {
	            view.getOutputLabel().setOutputLabelOpenQuery(i);
	            model.setTableSelected(i);    		
	            return;
    		}
    	}
    } // action performed
    
    

    public void parseTabResponse(TabResponse resp)
    {
//            selectTable.setTab(resp.getTab());
//            selectTable.setTabReceived(true);      
    }
    
    
    @ServiceActivator(inputChannel="tableStatusResponseChannel")
    public void tableStatusResponse(TableStatusResponse tableStatusResponse) {    	
    	System.out.println("response size: " + tableStatusResponse.getTableStatuses().keySet().size());
    	if (!isInitialised()) {
    		ArrayList<TableStatus> ts = new ArrayList<>();
    		for(Integer i : tableStatusResponse.getTableStatuses().keySet()) {
    			ts.add(tableStatusResponse.getTableStatuses().get(i));
    		}
    		init(ts);
    	}
    }

    
}
