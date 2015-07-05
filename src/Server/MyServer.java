
package Server;

import Message.Table;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Goldy
 */

public class MyServer implements Runnable
{
    public static MyServer server;
    
    // the lower bound of the port range
    private final ServerSocket socket;
    private final int PORT_NUMBER = 11000;
    private final int NUM_OF_TABLES = 44;
    private final Object LOCK = new Object();  
    private final ArrayList<ClientCommunicationThread> waiterClient;
    private final ArrayList<ClientCommunicationThread> tillClient;
    private ClientCommunicationThread barClient = null;
    private ClientCommunicationThread kitchenClient = null;
    private final Table[] tables;
    public Thread listenThread;
    public boolean socketListening;
    
    public MyServer() throws IOException
    {
        waiterClient = new ArrayList<>();
        tillClient = new ArrayList<>();
        tables = new Table[NUM_OF_TABLES + 1];
        
        // creates a thread for each table
        for (int i = 1; i <= NUM_OF_TABLES; i++)
            tables[i] = Table.createTable(i);
        
        socket = new ServerSocket(PORT_NUMBER);
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args)
    { 
        try
        {
           server = new MyServer();        
           server.start();
           
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
           server.end();

            
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
    
    /**
     *
     * @param number
     * @return
     */
    public Table getTable(int number)
    {
        if (number < 1 || number > NUM_OF_TABLES)
            return null;
        else return tables[number];
    } // table
    
    /**
     *
     * @return
     */
    public int getNumOfTables()
    {
        return NUM_OF_TABLES;
    }
    
    /**
     *
     * @return
     */
    public int getLowBoundPortRange()
    {
        return PORT_NUMBER;
    }

    /**
     *
     * @return
     */
    public ArrayList<ClientCommunicationThread> getWaiterClients()
    {
        synchronized(LOCK)
        {
            return waiterClient;
        } // sync
    }
    
        /**
     *
     * @return
     */
    public ArrayList<ClientCommunicationThread> getTillClients()
    {
        synchronized(LOCK)
        {
            return tillClient;
        } // sync
    }
    
    public boolean addWaiterClient(ClientCommunicationThread client)
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
    public void removeWaiterClient(ClientCommunicationThread client)
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
    public Table[] getTables()
    {
        return tables;
    }
    
    public ClientCommunicationThread getBarClient()
    {
        return barClient;
    }
    
    public void setBarClient(ClientCommunicationThread client)
    {
        barClient = client;
    }
    
    public ClientCommunicationThread getKitchenClient()
    {
        return kitchenClient;
    }
    
    public void setKitchenClient(ClientCommunicationThread client)
    {
        kitchenClient = client;
    }
    
    public boolean addTillClient(ClientCommunicationThread client)
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
    public void removeTillClient(ClientCommunicationThread client)
    {
        synchronized(LOCK)
        {
            for (int i = 0; i < tillClient.size(); i++)
                if (client.equals(tillClient.get(i)))
                    tillClient.remove(i);
            
            //debugGUI.addText("client removed");
        } // synchronized
    }

    @Override
    public void run() 
    {
        try
        {
            socketListening = true;
            //debugGUI.addText("Currently listening on port " + PORT_NUMBER);

            long clientNumber = 0;

            while (socketListening)
            {
                Socket acceptSocket = server.socket.accept();
                ClientCommunicationThread newThread = new ClientCommunicationThread(acceptSocket, clientNumber, this);
                newThread.getThread().start();

                //debugGUI.addText("accept client number " + clientNumber);
                clientNumber++;
            } // while  
        } // try
        catch (BindException e)
        {
            e.printStackTrace();
        } // catch 
        catch (IOException e)
        {
            System.out.println(e);
        } // catch
    }
    
    public void start()
    {
        this.listenThread.start();
    }
    
    public void end() throws IOException
    {
        socketListening = false;
        socket.close();
    }
    public static MyServer makeServer() throws IOException
    {
        MyServer server = new MyServer();  
        server.listenThread = new Thread(server);
        
        return server;
    }
} // MySocket class
