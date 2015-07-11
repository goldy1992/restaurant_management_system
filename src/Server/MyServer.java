
package Server;

import Message.Table;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Goldy
 */

public class MyServer implements Runnable
{
    public static MyServer server;
    private static final int PORT_NUMBER = 11000;
    private static final int NUM_OF_TABLES = 44;
    
    // the lower bound of the port range
    private final ServerSocket socket; 
    private final HashSet<ClientCommunicationThread> waiterClient;
    private final HashSet<ClientCommunicationThread> tillClient;
    
    private ClientCommunicationThread barClient = null;
    private ClientCommunicationThread kitchenClient = null;
    private final Table[] tables;
    public Thread listenThread;
    public boolean socketListening;
    
    public MyServer(ServerSocket socket,
                    HashSet<ClientCommunicationThread> waiterClient,
                    HashSet<ClientCommunicationThread> tillClient,
                    Table[] tables) throws IOException
    {
        this.waiterClient = waiterClient;
        this.tillClient = tillClient;
        this.tables = tables;
        
        // creates a thread for each table
        for (int i = 1; i <= tables.length -1; i++)
            tables[i] = Table.createTable(i);
        
        this.socket = socket;
    }
    
    
    /**
     *
     * @param args
     */
    public static void main(String[] args)
    { 
        try
        {
           server = makeServer(NUM_OF_TABLES, PORT_NUMBER);        
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
        return tables.length - 1;
    }

    /**
     *
     * @return
     */
    public HashSet<ClientCommunicationThread> getWaiterClients()
    {
        synchronized(waiterClient)
        {
            return waiterClient;
        } // sync
    }
    
        /**
     *
     * @return
     */
    public HashSet<ClientCommunicationThread> getTillClients()
    {
        synchronized(tillClient)
        {
            return tillClient;
        } // sync
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
    
    public static MyServer makeServer(int numOfTables, int portNumber) throws IOException
    {
        HashSet<ClientCommunicationThread> waiterClient = new HashSet<>();
        HashSet<ClientCommunicationThread> tillClient = new HashSet<>(); 
        Table[] tables = new Table[numOfTables + 1];
        ServerSocket socket = new ServerSocket(portNumber);
        MyServer newServer = new MyServer(socket, waiterClient, tillClient, tables);  
        newServer.listenThread = new Thread(newServer);
        
        return newServer;
    }
    
    /**
     *
     * @return
     */
    public static int getLowBoundPortRange()
    {
        return PORT_NUMBER;
    }
} // MySocket class
