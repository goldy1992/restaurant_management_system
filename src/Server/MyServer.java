
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */

public class MyServer
{
    // the lower bound of the port range
    private static final int LB_PORT_RANGE = 11000;
    private static final int NUM_OF_TABLES = 30;
    
    private static final Object LOCK = new Object();
    
    private static ArrayList<ClientCommunicationThread> clients;
    private static Table[] tables;
    
    public static void main(String[] args)
    {
        clients = new ArrayList<>();
        tables = new Table[NUM_OF_TABLES + 1];
        
        // creates a thread for each table
        for (int i = 1; i <= NUM_OF_TABLES; i++)
        {
            tables[i] = new Table(i);
            new Thread(tables[i]).start();
        } // for
        
        
        ServerSocket mySocket;
        boolean socketListening;
        
        try
        {
            System.out.println("got here");
            mySocket = new ServerSocket(LB_PORT_RANGE);
                System.out.println("socket listening");
            socketListening = true;
            System.out.println("Currently listening on port " + LB_PORT_RANGE);
            
            long clientNumber = 0;
            
            while (socketListening)
            {

                Socket acceptSocket = mySocket.accept();
                ClientCommunicationThread newThread = new ClientCommunicationThread(acceptSocket, clientNumber);
                newThread.getThread().start();
                clients.add(newThread);
                clientNumber++;
                System.out.println("client size " + clients.size());
            } // while
   
            mySocket.close();
            
        } // try
        catch (BindException e)
        {
            e.printStackTrace();
        } // catch 
        catch (IOException e)
        {
            System.out.println(e);
        } // catch   
        
    } // main
    
    public static Table getTable(int number)
    {
        if (number < 1 || number > NUM_OF_TABLES)
            return null;
        else return tables[number];
    } // table
    
    public static int getNumOfTables()
    {
        return NUM_OF_TABLES;
    }
    
    public static int getLowBoundPortRange()
    {
        return LB_PORT_RANGE;
    }

    public static ArrayList<ClientCommunicationThread> getClients()
    {
        synchronized(LOCK)
        {
            return clients;
        }
    }
    
    public static void removeClient(ClientCommunicationThread client)
    {
        synchronized(LOCK)
        {
            for (int i = 0; i < clients.size(); i++)
                if (client.equals(clients.get(i)))
                    clients.remove(i);
            
            System.out.println("client removed");
        } // synchronized
    }
    public static Table[] getTables()
    {
        return tables;
    }
} // MySocket class
