/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputPrinter;

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
public class OutputClient implements Runnable
{
    

    public static enum Type 
    {
        BAR, KITCHEN
    }
    
    private final Type type;

    private final Thread thread;
    
    public OutputClient(Type type)
    {
        this.thread = new Thread(this);
        this.type = type;
    } // constructor
    
    private static InetAddress serverAddress;
    private static int serverPort;  
    private static Socket client;    
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static OutputGUI gui;
    
    private static boolean isRunning = true;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {
       
       try
       {
System.out.println("argslength = " + args.length);
           
           
            serverAddress = InetAddress.getByName(null);
            serverPort = MyServer.getLowBoundPortRange();
            client = new Socket(serverAddress, serverPort);
            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
            gui = new OutputGUI();
            gui.addText("this client port " + client.getLocalPort());
            Type type;
            System.out.println();
            switch(args[0])
            {
                case "bar": type = Type.BAR; break;
                case "kitchen": type = Type.KITCHEN; break;
                default: type = Type.BAR; break;
            }
            OutputClient incomeThread = new OutputClient(type);
            incomeThread.getThread().start();
            

            if (type == Type.KITCHEN)
                gui.setTitle("Kitchen Client");
            else
                gui.setTitle("Bar Client");
            gui.setVisible(true);
            
            
            if (type == Type.KITCHEN)
            {
                RegisterClientRequest rKitchenRequest = new RegisterClientRequest(InetAddress.getByName(client.getLocalAddress().getHostName()),
                                                 InetAddress.getByName(serverAddress.getHostName()),
                                                 Message.generateRequestID(),
                                                 Request.RequestType.REGISTER_KITCHEN);         
                out.writeObject(rKitchenRequest);
            } // if
            else
            {
                RegisterClientRequest rBarRequest = new RegisterClientRequest(InetAddress.getByName(client.getLocalAddress().getHostName()),
                                                 InetAddress.getByName(serverAddress.getHostName()),
                                                 Message.generateRequestID(),
                                                 Request.RequestType.REGISTER_BAR);
            
                out.writeObject(rBarRequest);
                
            } // else
            
       }
       catch(IOException ex)
       {
            Logger.getLogger(OutputClient.class.getName()).log(Level.SEVERE, null, ex);         
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
                    // read the message
                    Message message = (Message) in.readObject();                    
                    
                    // convert message to notification
                    if (message instanceof EventNotification)
                    {
                        if (message instanceof NewItemNfn)
                        {
                            
                            
                            NewItemNfn newItemMessage = (NewItemNfn)message;
                            gui.addMessage(newItemMessage);

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
            Logger.getLogger(OutputClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(type + " received something");
        } // catch // catch

        
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

    

