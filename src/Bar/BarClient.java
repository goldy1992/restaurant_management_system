/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bar;

import Message.EventNotification.*;
import Message.Message;
import Message.Response.*;
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
public class BarClient implements Runnable
{
    private final Thread thread;
    
    public BarClient()
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
        
            BarClient incomeThread = new BarClient();
            incomeThread.getThread().start();
       }
       catch(IOException ex)
       {
            Logger.getLogger(BarClient.class.getName()).log(Level.SEVERE, null, ex);         
       }
    } // main

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
                            System.out.println(newItemMessage + "\n");
                        } // if
                    } // if
                    else if (message instanceof Response)
                    {
                        if (message instanceof RegisterBarResponse)
                        {
                            RegisterBarResponse rResponse = (RegisterBarResponse)message;
                            rResponse.onReceiving();
                        } // if
                    } // else if
                } // while
            } // if socket == null
        }
        catch (IOException | ClassNotFoundException ex) 
        {
            Logger.getLogger(BarClient.class.getName()).log(Level.SEVERE, null, ex);
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
