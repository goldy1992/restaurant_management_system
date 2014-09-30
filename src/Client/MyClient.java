/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import Server.MyServer;
import Server.Table;
import XML.Message.Message;
import XML.Message.Request.NumOfTablesRequest;
import XML.Message.Request.Request;
import XML.Message.Request.TableStatusRequest;
import XML.Message.Response.NumOfTablesResponse;
import XML.Message.Response.Response;
import XML.Message.Response.TableStatusResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Semaphore;
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
    private static int numberOfTables = -1;
    public static Semaphore lock1 = new Semaphore(1, true);
    public static Semaphore lock2 = new Semaphore(2, true);
    public static ArrayList<Table.TableStatus> tableStatuses = null;
    public static SelectTable selectTable;
    public static MyClient responseThread;
    public static ObjectOutputStream out = null;
    public boolean running = true;
    public final Thread thread;
    
    
    public MyClient()
    {
        thread = new Thread(this);
    }
    
   public static String generateRequestID()
   {
      String request_ID;
      Random random = new Random();
      int x = random.nextInt();
      request_ID = "" + x;
      Date currentDate = new Date();
      Timestamp t = new Timestamp(currentDate.getTime());
      request_ID = request_ID + t;
      return request_ID;
   } // generateRequestID
   
   public static ArrayList<Table.TableStatus> getTableStatuses()
   {
        ArrayList<Table.TableStatus> x = null;
  
        try 
        {
            lock1.acquire();
            x = tableStatuses;
            lock1.release();
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
      
          return x;
       
   }
   
   public static void setTableStatuses(ArrayList<Table.TableStatus>  x)
   {
        try 
        {
            lock1.acquire();
            tableStatuses = x;
            lock1.release();
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   public static int getNumTables()
   {
        int x = 0;
  
        try 
        {
            lock2.acquire();
            x = numberOfTables;
            lock2.release();
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
      
          return x;
       
   }
   
   public static void setNumTables(int x)
   {
        try 
        {
            lock2.acquire();
            numberOfTables = x;
            lock2.release();
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   

   
   public static void initialiseProgram()
   {
        try
        {
            serverAddress = InetAddress.getByName(null);
            serverPort = MyServer.getLowBoundPortRange();
            System.out.println(serverPort);
            client = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(MyClient.client.getOutputStream());
            System.out.println(client);
            responseThread = new MyClient();
            responseThread.thread.start();
            
            if (client == null)
                System.exit(0);

            NumOfTablesRequest nTablesRequest = new NumOfTablesRequest(InetAddress.getByName(client.getLocalAddress().getHostName()),
                                                 InetAddress.getByName(serverAddress.getHostName()),
                                                 generateRequestID(),
                                                 Request.RequestType.NUM_OF_TABLES);
            out.writeObject(nTablesRequest);
            System.out.println("sent num table request");

            ArrayList<Integer> tables = new ArrayList<>();
            for(int  i = 0 ; i < MyServer.getNumOfTables(); i++ ) 
                tables.add(i + 1);
                
            TableStatusRequest request = new TableStatusRequest(InetAddress.getByName(client.getLocalAddress().getHostName()),
                                          InetAddress.getByName(serverAddress.getHostName()),
                                          generateRequestID(),
                                          Request.RequestType.TABLE_STATUS,
                                          tables);
            out.writeObject(request);  
        } // try
        catch (IOException e)
        {
            System.out.println("Could not connect to server");
        } // catch
        

   }
    
    public static void main(String[] args) throws InterruptedException
    {
        initialiseProgram();
        System.out.println("pre while");
        
        
        while(getTableStatuses() == null && numberOfTables <= 0);
        
        //System.out.println(getTableStatuses());
        
        SelectTable t = new SelectTable(getTableStatuses());
            t.setVisible(true);

        
    } // main
    
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
                    if (response instanceof TableStatusResponse)
                    {
                        TableStatusResponse r = (TableStatusResponse)response;

                                MyClient.setTableStatuses(r.getTableStatuses());
                            
                        
                        //System.out.println("reply for table statuses in client" + MyClient.tableStatuses);
                       
                    }
                    else if (response instanceof NumOfTablesResponse)
                    {
                        NumOfTablesResponse r = (NumOfTablesResponse)response;
                        System.out.println("got  num of tables: " + r.getNumOfTables());
                        MyClient.setNumTables(r.getNumOfTables());

                    }
                                            System.out.println(response);
                }
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} // MyClientSocketClass
