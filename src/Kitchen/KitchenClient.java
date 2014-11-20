/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kitchen;

import Item.Item;
import Message.EventNotification.EventNotification;
import Message.EventNotification.NewItemNfn;
import Message.Message;
import Message.Request.RegisterClientRequest;
import Message.Request.Request;
import Message.Response.RegisterClientResponse;
import Message.Response.Response;
import Server.MyServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbbx9mg3
 */
public class KitchenClient implements Runnable
{


    private final Thread thread;
    
    public KitchenClient()
    {
        this.thread = new Thread(this);
    } // constructor
    
    /**
     *
     */
    private static InetAddress serverAddress;
    /**
     *
     */
    private static int serverPort;  

    /**
     *
     */
    private static Socket client; 
    
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    
    private static boolean isRunning = true;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {
       
       try
       {
            serverAddress = InetAddress.getByName(null);
            serverPort = MyServer.getLowBoundPortRange();
            client = new Socket(serverAddress, serverPort);
            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
        
            KitchenClient incomeThread = new KitchenClient();
            incomeThread.getThread().start();
            
            RegisterClientRequest rKitchenRequest = new RegisterClientRequest(InetAddress.getByName(client.getLocalAddress().getHostName()),
                                                 InetAddress.getByName(serverAddress.getHostName()),
                                                 Message.generateRequestID(),
                                                 Request.RequestType.REGISTER_KITCHEN);         
            out.writeObject(rKitchenRequest);
            
            
       }
       catch(IOException ex)
       {
            Logger.getLogger(KitchenClient.class.getName()).log(Level.SEVERE, null, ex);         
       }
    } // main
    
    private static String sortTimeSyntax(int time)
    {        
        if (time == 0) return "00";
        else if (time > 0 && time <= 9)
            return "0" + time;
        else return time + "";
        
    } // sortTimeSyntax

    public static String timeToString(int hours, int minutes)
    {
        return sortTimeSyntax(hours) + ":" + sortTimeSyntax(minutes);
    }



    @Override
    public void run() 
    {
        try
        {
            if (client != null)
            {
                System.out.println(client);
                System.out.println("read message");
                while(isRunning)
                {               
                    Message message = (Message) in.readObject();                    
                    if (message instanceof EventNotification)
                    {
                        if (message instanceof NewItemNfn)
                        {
                            NewItemNfn newItemMessage = (NewItemNfn)message;
                            System.out.println("Table " +  newItemMessage.getTable().getTableNumber() + " " + KitchenClient.timeToString(newItemMessage.getHours(), newItemMessage.getMinutes()) );
                            for (Item i : newItemMessage.getItems())
                                System.out.println(i.getQuantity() + "\t" + i.getName());
                            
                            System.out.println("");
                        } // if
                    } // if
                    else if (message instanceof Response)
                    {
                        if (message instanceof RegisterClientResponse)
                        {
                            RegisterClientResponse rResponse = (RegisterClientResponse)message;
                            rResponse.onReceiving();
                        } // if
                    } // else if
                } // while
            } // if socket == null
        }
        catch (IOException | ClassNotFoundException ex) 
        {
            Logger.getLogger(KitchenClient.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
        
    } // run
    
    /**
     *
     * @return
     */
    public Thread getThread()
    {
        return thread;
    }
    
    public static boolean getRunning()
    {
        return isRunning;
    }
    
    public static void setRunning(boolean running)
    {
        isRunning = running;
    } // setRunning
    
} // class

    

