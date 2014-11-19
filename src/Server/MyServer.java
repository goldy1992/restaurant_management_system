
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
    private static final int PORT_NUMBER = 11000;
    private static final int NUM_OF_TABLES = 44;
    private static final Object LOCK = new Object();  
    private static ArrayList<ClientCommunicationThread> clients;
    private static ClientCommunicationThread barClient = null;
    private static Table[] tables;
    
    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        clients = new ArrayList<>();
        tables = new Table[NUM_OF_TABLES + 1];
        
        // creates a thread for each table
        for (int i = 1; i <= NUM_OF_TABLES; i++)
            tables[i] = Table.createTable(i);
            //new Thread(tables[i]).start();
        
        
        ServerSocket mySocket;
        boolean socketListening;
        
        try
        {
            System.out.println("got here");
            mySocket = new ServerSocket(PORT_NUMBER);
                System.out.println("socket listening");
            socketListening = true;
            System.out.println("Currently listening on port " + PORT_NUMBER);
            
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
            
        } // try // try
        catch (BindException e)
        {
            e.printStackTrace();
        } // catch 
        catch (IOException e)
        {
            System.out.println(e);
        } // catch   
        
    } // main
    
    /**
     *
     * @param number
     * @return
     */
    public static Table getTable(int number)
    {
        if (number < 1 || number > NUM_OF_TABLES)
            return null;
        else return tables[number];
    } // table
    
    /**
     *
     * @return
     */
    public static int getNumOfTables()
    {
        return NUM_OF_TABLES;
    }
    
    /**
     *
     * @return
     */
    public static int getLowBoundPortRange()
    {
        return PORT_NUMBER;
    }

    /**
     *
     * @return
     */
    public static ArrayList<ClientCommunicationThread> getClients()
    {
        synchronized(LOCK)
        {
            return clients;
        } // sync
    }
    
    /**
     *
     * @param client
     */
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

    /**
     *
     * @return
     */
    public static Table[] getTables()
    {
        return tables;
    }
    
    public static ClientCommunicationThread getBarClient()
    {
        return barClient;
    }
    
    public static void setBarClient(ClientCommunicationThread client)
    {
        barClient = client;
    }
} // MySocket class
