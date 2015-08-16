/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Message.Table;
import Message.TableList;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Mike
 */
public class StartServer 
{
    private static final int PORT_NUMBER = 11000;
    private static final int NUM_OF_TABLES = 44;
    
    public static void main(String[] args)
    { 
        try
        {
           Server server = makeServer(NUM_OF_TABLES, getPORT_NUMBER());        
           
           boolean exit = false;
           while(!exit)
           {
                Scanner sc = new Scanner(System.in);
    
                while (sc.hasNextLine())
                {
                    String s = sc.nextLine();
                    if (s.equals("exit"))
                        exit = true;
                }
           }


            
        } // try // try // try // try // try // try // try // try
        catch (BindException e)
        {
            e.printStackTrace();
        } // catch 
        catch (IOException e)
        {
            System.out.println(e);
        } // catch   
        
    } // main
    
    public static Server makeServer(int numOfTables, int portNumber) throws IOException
    {
        HashSet<ClientConnection> waiterClient = new HashSet<>();
        HashSet<ClientConnection> tillClient = new HashSet<>(); 
        Table[] tables = new Table[numOfTables + 1];

        // creates a thread for each table
        for (int i = 1; i <= tables.length -1; i++)
            tables[i] = Table.createTable(i);
        
        TableList tableList = new TableList(tables);
        ServerSocket socket = new ServerSocket(portNumber);
        Server newServer = new Server(socket, waiterClient, tillClient, tableList);  
        ServerRunThread serverThread = new ServerRunThread();
        serverThread.setServer(newServer);
        
        return newServer;
    } // makeServer

    /**
     * @return the PORT_NUMBER
     */
    public static int getPORT_NUMBER() {
        return PORT_NUMBER;
    }
    
}
