package Server;

import Message.TableList;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 *
 * @author Goldy
 */
public class Server implements Runnable
{    
    private final ServerSocket socket; 
    private final HashSet<ClientConnection> waiterClient;
    private final HashSet<ClientConnection> tillClient;
    
    private ClientConnection barClient = null;
    private ClientConnection kitchenClient = null;
    private final TableList tables;
    public Thread listenThread;
    public boolean socketListening;
    
    public Server(ServerSocket socket,
                    HashSet<ClientConnection> waiterClient,
                    HashSet<ClientConnection> tillClient,
                    TableList tables) throws IOException
    {
        this.waiterClient = waiterClient;
        this.tillClient = tillClient;
        this.tables = tables; 
        this.socket = socket;
    } // myserver
    

    public TableList getTables()
    {
        return tables;
    } // table

    /**
     * @return A HashSet of all the waiter clients
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
     * @return A hash set of all the till clients
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
        synchronized(barClient)
        {
            barClient = client;
        }
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
                Socket acceptSocket = socket.accept();
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

} // MySocket class
