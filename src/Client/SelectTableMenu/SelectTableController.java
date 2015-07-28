/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.SelectTableMenu;

import Client.SelectTableMenu.View.SelectTableView;
import Client.MainMenu.Menu;
import static Client.SelectTableMenu.SelectTableModel.NO_TABLE_SELECTED;
import Client.WaiterClient;
import Item.Tab;
import Message.Table.TableStatus;
import static Message.Table.TableStatus.DIRTY;
import static Message.Table.TableStatus.IN_USE;
import static Message.Table.TableStatus.OCCUPIED;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public class SelectTableController implements ActionListener 
{
    public SelectTableModel model;
    public SelectTableView view;
    public final WaiterClient parentClient;
    public final Object tabLock = new Object();
    public Boolean tabReceived = false;
    public Menu menu; // the menu GUI
    
    public SelectTableController(ArrayList<TableStatus> tableStatuses,
         WaiterClient parent)
    {
        this.model = new SelectTableModel(tableStatuses);
        this.view = new SelectTableView(this, tableStatuses);
         view.setVisible(true);
        this.parentClient = parent;
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
                //parentClient.debugGUI.addText("select table " +  getTableSelected());
            } // if
        }
        
        int tableSelected = model.getTableSelected();
        if (e.getSource() == view.cleanTable)
        {
            TableStatus selectedTableStatus = model.getTableStatus(tableSelected);
            if (selectedTableStatus == DIRTY)
            {
                parentClient.sendTableStatusEventNotification(tableSelected, 
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
                parentClient.sendTableStatusEventNotification(tableSelected,  IN_USE);
                parentClient.sendTabRequest(tableSelected);
          
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
                    
                menu = Menu.makeMenu(parentClient, this.view, model.getTab());
                //MyClient.debugGUI.addText("menu has been made");

                this.tabReceived = false;
                parentClient.sendTableStatusEventNotification(tableSelected, OCCUPIED);

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
