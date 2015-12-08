/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client;

import com.mike.client.MainMenu.BarTabDialogSelect;
import com.mike.item.Tab;
import com.mike.message.EventNotification.*;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import static com.mike.message.Request.RegisterClientRequest.ClientType.TILL;
import com.mike.message.Request.TableStatusRequest;
import com.mike.message.Response.*;
import com.mike.message.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbbx9mg3
 */
public class TillClient extends UserClient 
{
    public TillGUI gui = null;
    
    public TillClient(ClientType  type) 
    {
        super(type);
    } // constructor
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {   
        final TillClient myClient;
      
            myClient = (TillClient)Client.makeClient(TILL);
            
            ArrayList<Integer> tables = new ArrayList<>();
            // add null because there's no table zero
            tables.add(-1);
            TableStatusRequest request = new TableStatusRequest(tables);
           // myClient.getOutputStream().writeObject(request);  
            myClient.gui = new TillGUI(myClient);
            myClient.gui.setTitle("Till");
            myClient.gui.setVisible(true);
         
       
        System.out.println("end of till client");
    } // main
      
    
    public ArrayList<Table.TableStatus> getTableStatuses()
    {
        return tableStatuses;
    }

    @Override
    protected void parseTableStatusEvtNfn(TableStatusEvtNfn event) 
    {

        if (tableStatuses != null)
        {
                    System.out.println("notification for table status received");
            this.tableStatuses.set(event.getTableNumber(), event.getTableStatus());
            if (this.gui.getMenu() != null)
            {
                System.out.println("updating buttons");
                this.gui.getMenu().updateButtons((ArrayList<Table.TableStatus>)tableStatuses.clone());
            }
            else
              System.out.println("gui is null");
        }
    } //  parseTableStatusEvtNfn

    @Override
    public void parseTabResponse(TabResponse resp) 
    {
        synchronized(this.gui.getMenu().selectorFrame.lock)
        {            
            Tab t = resp.getTab();
            if(this.gui.getMenu().selectorFrame.getState() == BarTabDialogSelect.Functionality.GET_TAB)
                gui.getMenu().setUpTab(t);
            else
                gui.getMenu().setOldTab(t);
            gui.getMenu().selectorFrame.setTabReceived(true); 
            this.gui.getMenu().selectorFrame.lock.notifyAll();
        }
    } // parseTabResponse
    

       
    
} // class

    

