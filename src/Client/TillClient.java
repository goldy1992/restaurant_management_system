/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.Client;
import Client.MainMenu.Menu;
import Client.MyClient;
import Client.MainMenu.TillMenu;
import Item.Item;
import Message.Message;
import Message.Request.*;
import Message.EventNotification.*;
import Message.Response.*;
import Server.MyServer;
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
 * @author mbbx9mg3
 */
public class TillClient extends Client implements Runnable
{
    private final Thread responseThread;
    
    public TillClient()
    {
        this.responseThread = new Thread(this);
    } // constructor
    

    private static InetAddress serverAddress;
    private static int serverPort;  
    private static Socket client; 
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static boolean isRunning = true;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {   
        TillClient myClient = new TillClient();

        TillMenu menu = TillMenu.makeMenu(myClient, null, null, out, TillMenu.class);

    } // main
    


    
    /**
     *
     * @return
     */
    public Thread getThread()
    {
        return responseThread;
    }
    
    public static boolean getRunning()
    {
        return isRunning;
    }
    
    public static void setRunning(boolean running)
    {
        isRunning = running;
    } // setRunning

    @Override
    public void parseResponse(Response response) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void parseEventNotification(EventNotification evntNfn) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
} // class

    

