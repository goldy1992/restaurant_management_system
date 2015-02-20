
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Message.Table;
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
    private static ArrayList<ClientCommunicationThread> waiterClient;
    private static ArrayList<ClientCommunicationThread> tillClient;
    private static ClientCommunicationThread barClient = null;
    private static ClientCommunicationThread kitchenClient = null;
    private static Table[] tables;
    
    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {

        waiterClient = new ArrayList<>();
        tillClient = new ArrayList<>();
        tables = new Table[NUM_OF_TABLES + 1];
        
        // creates a thread for each table
        for (int i = 1; i <= NUM_OF_TABLES; i++)
            tables[i] = Table.createTable(i);
            //new Thread(tables[i]).start();
        
        ServerSocket mySocket;
        boolean socketListening;
        
        try
        {
            //debugGUI.addText("got here");
            mySocket = new ServerSocket(PORT_NUMBER);
              //  debugGUI.addText("socket listening");
            socketListening = true;
            //debugGUI.addText("Currently listening on port " + PORT_NUMBER);
            
            long clientNumber = 0;
            
            while (socketListening)
            {
                Socket acceptSocket = mySocket.accept();
                ClientCommunicationThread newThread = new ClientCommunicationThread(acceptSocket, clientNumber);
                newThread.getThread().start();

                //debugGUI.addText("accept client number " + clientNumber);
                clientNumber++;
            } // while
   
            mySocket.close();
            
        } // try // try // try // try
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
    public static ArrayList<ClientCommunicationThread> getWaiterClients()
    {
        synchronized(LOCK)
        {
            return waiterClient;
        } // sync
    }
    
    public static boolean addWaiterClient(ClientCommunicationThread client)
    {
        synchronized(LOCK)
        {
            for (ClientCommunicationThread waiterClient1 : waiterClient) 
            {
                if (client.equals(waiterClient1)) {
                    //debugGUI.addText("client already exits");
                    return false;
                }
            }
            waiterClient.add(client);
            return true;
     
        } // synchronized
    }
    
    /**
     *
     * @param client
     */
    public static void removeWaiterClient(ClientCommunicationThread client)
    {
        synchronized(LOCK)
        {
            for (int i = 0; i < waiterClient.size(); i++)
                if (client.equals(waiterClient.get(i)))
                    waiterClient.remove(i);
            
            //debugGUI.addText("client removed");
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
    
    public static ClientCommunicationThread getKitchenClient()
    {
        return kitchenClient;
    }
    
    public static void setKitchenClient(ClientCommunicationThread client)
    {
        kitchenClient = client;
    }
    
    public static boolean addTillClient(ClientCommunicationThread client)
    {
        synchronized(LOCK)
        {
            for (ClientCommunicationThread tillClient1 : tillClient) 
            {
                if (client.equals(tillClient1)) {
                    //debugGUI.addText("client already exits");
                    return false;
                }
            }
            tillClient.add(client);
            return true;
     
        } // synchronized
    }
    
    /**
     *
     * @param client
     */
    public static void removeTillClient(ClientCommunicationThread client)
    {
        synchronized(LOCK)
        {
            for (int i = 0; i < tillClient.size(); i++)
                if (client.equals(tillClient.get(i)))
                    tillClient.remove(i);
            
            //debugGUI.addText("client removed");
        } // synchronized
    }
} // MySocket class
