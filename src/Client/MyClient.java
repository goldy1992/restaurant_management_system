/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import Message.EventNotification.EventNotification;
import Message.EventNotification.TableStatusEvtNfn;
import Server.MyServer;
import Server.Table;
import Message.Message;
import Message.Request.NumOfTablesRequest;
import Message.Request.RegisterClientRequest;
import Message.Request.Request;
import Message.Request.TableStatusRequest;
import Message.Response.Response;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Goldy
 */
public class MyClient implements Runnable
{

    public static InetAddress serverAddress; 
    public static int serverPort;  
    public static Socket client;
    public static SelectTable selectTable; // selectTableGUI
    public static MyClient responseThread;
    /**
     * An object used to ensure tasks are performed asynchronously. 
     */
    public static final Object lock = new Object();

    private static ArrayList<Table.TableStatus> tableStatuses = null; // temp variable
    private static int numberOfTables = -1;
    private static ObjectOutputStream out = null;
    
    // object classes
    public boolean running = true;
    public final Thread thread;

    
    
    public MyClient()
    {
        thread = new Thread(this);
    } // constructor
    
   
    /**
     *
     * @param x
     */
   public static void setTableStatuses(ArrayList<Table.TableStatus>  x)
   {
        tableStatuses = x;    
   }
   
    /**
     * @return The number of tables in use.
     */
    public static int getNumTables()
   {
        return numberOfTables;
   }
   
    /**
     * A mutator method to set the number of tables.
     * @param numTables the number of the tables to be run
     */
    public static void setNumTables(int numTables)
   {
        numberOfTables = numTables;   
   }
   
    /**
     * @return The output stream of the client.
     */
    public ObjectOutputStream getOutputStream()
   {
       return out;
   }
    
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        try
        {
            serverAddress = InetAddress.getByName(null);
            serverPort = MyServer.getLowBoundPortRange();
            client = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(MyClient.client.getOutputStream());
            responseThread = new MyClient();
            responseThread.thread.start();
            
            if (client == null)
                System.exit(0);
            
            RegisterClientRequest rWRequest = new RegisterClientRequest(InetAddress.getByName(client.getLocalAddress().getHostName()),
                                            InetAddress.getByName(serverAddress.getHostName()),
                                            Message.generateRequestID(),
                                            Request.RequestType.REGISTER_WAITER_CLIENT);         
                out.writeObject(rWRequest);

            NumOfTablesRequest nTablesRequest = new NumOfTablesRequest(InetAddress.getByName(client.getLocalAddress().getHostName()),
                                                 InetAddress.getByName(serverAddress.getHostName()),
                                                 Message.generateRequestID(),
                                                 Request.RequestType.NUM_OF_TABLES);
            out.writeObject(nTablesRequest);
            //System.out.println("sent num table request");

            ArrayList<Integer> tables = new ArrayList<>();
            // add null because there's no table zero
            tables.add(null);
            for(int  i = 1 ; i <= MyServer.getNumOfTables(); i++ ) 
                tables.add(i);
                
            TableStatusRequest request = new TableStatusRequest(InetAddress.getByName(client.getLocalAddress().getHostName()),
                                          InetAddress.getByName(serverAddress.getHostName()),
                                          Message.generateRequestID(),
                                          Request.RequestType.TABLE_STATUS,
                                          tables);
            out.writeObject(request);  
        } // try
        catch (IOException e)
        {
            System.out.println("Could not connect to server");
        } // catch
        System.out.println("pre while");
        
        
        synchronized(lock)
        {
            while(tableStatuses == null)
            {
                try { lock.wait(); }
                catch (InterruptedException e) 
                { // treat interrupt as exit request
                    break;
                }
            } // while
        
        } // synchronized
        System.out.println("post while");
        
        selectTable = new SelectTable(tableStatuses, out);
        selectTable.setVisible(true);

        
    } // main
    
    /**
     * The run method that controls the listener that receives incoming messages
     * from the rest of the System.
     */
    @Override
    public void run()
    {       
        try 
        {   
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            while(running)
            {
                Message response = (Message)in.readObject();
                
                if (response instanceof Response)
                {
                    Response resp = (Response) response;
                    resp.onReceiving();                                         
                } // if response
                else if(response instanceof EventNotification)
                {
                    if (response instanceof TableStatusEvtNfn)
                    {
                        TableStatusEvtNfn r = (TableStatusEvtNfn)response; 
                        //System.out.println("table number " + r.getTableNumber() +"\n" + "table Status: " + r.getTableStatus());
                        selectTable.setTableStatus(r.getTableNumber(), r.getTableStatus());
                        // System.out.println("table number updated to " + selectTable.getTableStatus(r.getTableNumber()));
                    } // inner if
                } // evt ntfn if
            } // while running
        } // try
        catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} // MyClientSocketClass
