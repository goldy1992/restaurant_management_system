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
import com.mike.message.Table.TableStatus;
import static com.mike.message.Table.TableStatus.DIRTY;
import static com.mike.message.Table.TableStatus.IN_USE;
import static com.mike.message.Table.TableStatus.OCCUPIED;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mike
 */
public class SelectTableController implements ActionListener 
{
	@Autowired
	MessageSender messageSender;
	
	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	
    public SelectTableModel model;
    public SelectTableView view;
    public Object tabLock = new Object();
    public Boolean tabReceived = false;
    private boolean initialised = false;
    
    public boolean isInitialised() {
		return initialised;
	}



	public void setInitialised(boolean initialised) {
		this.initialised = initialised;
	}



	public void init(ArrayList<TableStatus> tableStatuses) {
        this.model = new SelectTableModel(tableStatuses);
        this.view = new SelectTableView(this, tableStatuses);
         view.setVisible(true);  	
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
    @SuppressWarnings("empty-statement")
    public void actionPerformed(ActionEvent e) 
    {
        for (int i = 0; i <  view.getTableButtons().length; i++)
        {
            if (e.getSource() == view.getTableButtons()[i])
            {
                view.getOutputLabel().setOutputLabelOpenQuery(i);
                model.setTableSelected(i);
            } // if
        }
        
        int tableSelected = model.getTableSelected();
        if (e.getSource() == view.cleanTable)
        {
            TableStatus selectedTableStatus = model.getTableStatus(tableSelected);
            if (selectedTableStatus == DIRTY)
            {
                messageSender.sendTableStatusEventNotification(tableSelected, 
                                                     selectedTableStatus);
            } // if
        } // if getSource

        
        if (e.getSource() == view.openTable)
        {
            if (tableSelected == NO_TABLE_SELECTED)
            {
                view.getOutputLabel().setOutputLabelNoTableSelected();
            }
            else if (model.getTableStatus(tableSelected) == IN_USE)
            {
                view.getOutputLabel().setOutputLabelTableInUse(tableSelected);
            } // else if
            else
            {
              //  parentClient.sendTableStatusEventNotification(tableSelected,  IN_USE);
               // parentClient.sendTabRequest(tableSelected);
          
                synchronized(tabReceived)
                {
                    try
                    {
                        while(this.tabReceived == false)
                            tabReceived.wait();
                    }
                    catch (InterruptedException ex) 
                    {
                        System.err.println(ex);
                    } // catch // catch
                } // synchronized
                    
              //  menu = Menu.makeMenu(parentClient, this.view, model.getTab());

                this.tabReceived = false;
                messageSender.sendTableStatusEventNotification(tableSelected, OCCUPIED);

                tableSelected = NO_TABLE_SELECTED;
            } // else
            
        } // if open table
        
        if(e.getSource() == view.moveTable)
        {
            
        } // if
    }
    
    public void setTabReceived(boolean received)
    {
        this.tabReceived = received;
    }
    
    public void setTab(Tab tab)
    {
        model.setTab(tab);
    }
    
}
