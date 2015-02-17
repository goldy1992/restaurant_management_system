/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.EventNotification.EventNotification;
import Message.Message;
import Message.Response.Response;
import OutputPrinter.OutputGUI;
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
public abstract class Client implements Runnable
{
    public InetAddress serverAddress; 
    public int serverPort;  
    public Socket client;
    public OutputGUI debugGUI;
    public Thread responseThread;    
    private ObjectOutputStream out = null;
    
    // object classes
    public boolean running = true;
    public Thread thread; 
    
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
                    parseResponse((Response)response);
                else if(response instanceof EventNotification)
                    parseEventNotification((EventNotification)response);
            } // while running
        } // try
        catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Client()
    {
        try
        {
            debugGUI = new OutputGUI();
            debugGUI.setTitle("Client Output");
            debugGUI.setVisible(true);
            serverAddress = InetAddress.getByName(null);
            serverPort = MyServer.getLowBoundPortRange();
            client = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(client.getOutputStream());
            responseThread = new Thread();
            responseThread.start();
        } // try
        catch (IOException e)
        {
            debugGUI.addText("Could not connect to server");
        } // catch
        debugGUI.addText("pre while");
    }
    
        /**
     * @return The output stream of the client.
     */
   public ObjectOutputStream getOutputStream()
   {
       return out;
   }
   
    public abstract void parseResponse(Response response) throws IOException, ClassNotFoundException;
   
    public abstract void parseEventNotification(EventNotification evntNfn) throws IOException, ClassNotFoundException;
   
    public String sortTimeSyntax(int time)
    {        
        if (time == 0) return "00";
        else if (time > 0 && time <= 9)
            return "0" + time;
        else return time + "";
        
    } // sortTimeSyntax

    public String timeToString(int hours, int minutes)
    {
        return sortTimeSyntax(hours) + ":" + sortTimeSyntax(minutes);
    }
   

}
