/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bar;

import Message.EventNotification.EventNotification;
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
        
        //BarClient incomeThread = new BarClient(); EDIT THIS
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
                    EventNotification message = (EventNotification) in.readObject();                    
                    if (message instanceof EventNotification);
                      
                } // while
            } // if socket == null
        }
        catch (IOException | ClassNotFoundException ex) 
        {
            Logger.getLogger(BarClient.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
        
    } // run
} // class
