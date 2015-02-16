/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import OutputPrinter.OutputGUI;
import Server.MyServer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

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
    public abstract void run();
    
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
}
