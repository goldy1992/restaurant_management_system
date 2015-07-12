package Server;

import Message.TableList;
import Message.Table;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
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
    private final HashSet<ClientConnection> waiterClient;
    private final HashSet<ClientConnection> tillClient;
    
    private ClientConnection barClient = null;
    private ClientConnection kitchenClient = null;
    private final TableList tables;
    public Thread listenThread;
    public boolean socketListening;
    
    public MyServer(ServerSocket socket,
                    HashSet<ClientConnection> waiterClient,
                    HashSet<ClientConnection> tillClient,
                    TableList tables) throws IOException
    {
        this.waiterClient = waiterClient;
        this.tillClient = tillClient;
        this.tables = tables; 
        this.socket = socket;
    } // myserver
    
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
    public TableList getTables()
    {
        return tables;
    } // table

    /**
     *
     * @return
     */
    public HashSet<ClientConnection> getWaiterClients()
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
    public HashSet<ClientConnection> getTillClients()
    {
        synchronized(tillClient)
        {
            return tillClient;
        } // sync
    }
    
    public ClientConnection getBarClient()
    {
        return barClient;
    }
    
    public void setBarClient(ClientConnection client)
    {
        barClient = client;
    }
    
    public ClientConnection getKitchenClient()
    {
        return kitchenClient;
    }
    
    public void setKitchenClient(ClientConnection client)
    {
        kitchenClient = client;
    }

    @Override
    public void run() 
    {
        try
        {
            socketListening = true;
            long clientNumber = 0;

            while (socketListening)
            {
                Socket acceptSocket = server.socket.accept();
                ClientConnection newThread = 
                    ClientConnection.makeClientThread(acceptSocket, 
                                                    clientNumber, 
                                                    this);
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
        HashSet<ClientConnection> waiterClient = new HashSet<>();
        HashSet<ClientConnection> tillClient = new HashSet<>(); 
        Table[] tables = new Table[numOfTables + 1];

        // creates a thread for each table
        for (int i = 1; i <= tables.length -1; i++)
            tables[i] = Table.createTable(i);
        
        TableList tableList = new TableList(tables);
        ServerSocket socket = new ServerSocket(portNumber);
        MyServer newServer = new MyServer(socket, waiterClient, tillClient, tableList);  
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
