/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author Goldy
 * 
 * The class to represent a table in the restaurant
 */
public class Table implements Runnable
{
    
    public static enum TableStatus 
    {
        FREE, OCCUPIED, IN_USE, 
    }
    // the table number
    private int tableNumber;
    
    // the table number Port
    private int tableNumberPort;
    
    
    // is boolean
    private TableStatus tableStatus;
    
    public Table(int tableNumber)
    {
        //System.out.println("table number  " + tableNumber);
        this.tableNumber = tableNumber;
        this.tableNumberPort = tableNumber + MyServer.getLowBoundPortRange();
        tableStatus = TableStatus.FREE;
        
        /* TEST */
        //int pick = new Random().nextInt(TableStatus.values().length);
        //tableStatus =  TableStatus.values()[pick];
        
    } // constructor
    
    public int getTableNumber()
    {
        return tableNumber;
    } // getTableNumber
    
    public int getTablePortNumber()
    {
        return tableNumberPort;
    } // getTablePortNumber
    
    public TableStatus getTableStatus()
    {
        return tableStatus;
    }
    
    public void setTableStatus(TableStatus status)
    {
        tableStatus = status;
    }
    
    public void run()
    {
        ServerSocket mySocket = null;
        boolean socketListening = false;
        
        try
        {
            mySocket = new ServerSocket(tableNumberPort);
            socketListening = true;
            System.out.println("Currently listening on port " + this.tableNumberPort);
        } // try
        catch (IOException e)
        {
            System.out.println("Could not listen on port " + this.tableNumberPort);
        } // catch   

        while ( mySocket!= null && socketListening)
        {
            try
            (
                Socket acceptSocket = mySocket.accept();
                PrintWriter out =
                    new PrintWriter(acceptSocket.getOutputStream(), true);                   
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(acceptSocket.getInputStream()));   
            ) // try open bracket
            {
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
            if (mySocket!= null)
                mySocket.close();
        } // try
        catch (IOException e)
        {
            System.out.println("Failed to accept incoming socket");
        } // catch
    } // run
}
