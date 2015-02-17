/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.MainMenu.TillMenu;
import Message.EventNotification.*;
import Message.Response.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author mbbx9mg3
 */
public class TillClient extends Client implements Runnable
{
    
    public TillClient()
    {
        super();
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
        TillClient myClient = Client.makeClient(TillClient.class);

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

    

