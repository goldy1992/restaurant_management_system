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
public class Table implements Serializable 
{

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
    /**
     *
     */
    public static enum TableStatus 
    {

        /**
         *
         */
        FREE, 

        /**
         *
         */
        OCCUPIED, 

        /**
         *
         */
        IN_USE, 
        
        DIRTY
    }
    /**
     * the table number
     */
    private int tableNumber;
    private TableStatus tableStatus;   
    private Tab currentTab;
 //s   private ServerSocket serverSocket;
    
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
     * @return
     */

    
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
    public void setTableStatus(TableStatus status)
    {
        tableStatus = status;
    }
    
    public void updateTab(Tab tab) {
        this.setCurrentTab(tab);
    }
    
/*    @Override
    public void run() {
        boolean socketListening = true;
        System.out.println("Currently listening on port ");
        while ( serverSocket!= null && socketListening) {
            try (
                Socket acceptSocket = serverSocket.accept();
                PrintWriter out =
                    new PrintWriter(acceptSocket.getOutputStream(), true);                   
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(acceptSocket.getInputStream()));   
            ) {
                String inputLine;
                if ((inputLine = in.readLine()) != null) 
                {
                    
                } // if
            } // TRY
            catch (IOException e)
            {
                System.out.println("Failed to accept incoming socket");
            } // catch            
        } // while
        try
        {
            if (serverSocket!= null)
                serverSocket.close();
        } // try
        catch (IOException e)
        {
            System.out.println("Failed to accept incoming socket");
        } // catch
    } // run */
    
    
    
}
