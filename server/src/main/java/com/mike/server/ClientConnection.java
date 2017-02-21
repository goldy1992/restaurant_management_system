package com.mike.server;

import com.mike.message.*;
//import Client.OutputGUI;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Goldy
 *
 * LEGACY CODE
 */
public class ClientConnection implements Runnable {
    private final Server parent;
    private final Socket socket;
    private Thread thread;
    public long id;
    private ObjectOutputStream out;
    private ObjectInputStream in;   
    public boolean isRunning;
   // public OutputGUI gui;
    
    /**
     *
     * @param socket
     * @param id
     * @param parent
     * @throws java.net.SocketException
     */
    public ClientConnection(Socket socket, long id, Server parent) throws SocketException
    {
        this.socket = socket;
        this.id = id;
        this.parent = parent;
        this.isRunning = true;
        //gui = new OutputGUI();
        System.out.println("buffer size: " + socket.getReceiveBufferSize());
        //gui.setVisible(true);
    } // constructor
    
    public void setThread(Thread t)
    {
        this.thread = t;
    }
    
    public void setIn(ObjectInputStream in)
    {
        this.in = in;
    }
    
    public void setOut(ObjectOutputStream out)
    {
        this.out = out;
    }
   
    @Override
    public void run()
    {
        if (socket != null)
        {
            Message message = null;
            try
            {                              
                while(isRunning)
                {
                    message = (Message) in.readObject();                        
                  //  ParseMessage parse = new ParseMessage(message, this);
                    //parse.parse();
                } // while
            } // try        
            catch (EOFException e)
            {
                System.err.println(e.getCause() 
                        + "\n " + e.getLocalizedMessage() 
                        +  "\n  " + e.getMessage());
                Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, e);
                
            }
            catch(IOException e)
            {
                System.err.println(e);
            } // catch
            catch (ClassNotFoundException  ex) 
            {
                Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            } // catch             // catch             // catch             // catch            
        } // if socket == null
    } // run
    
    public void stop()
    {
        this.isRunning = false;
    }
   
    /**
     *
     * @return
     */
    public Socket getSocket()
    {
        return socket;
    }
    
    /**
     *
     * @return
     */
    public Thread getThread()
    {
        return thread;
    }
    
    /**
     *
     * @return
     */
    public ObjectOutputStream getOutStream()
    {
        return out;
    }
    
    /**
     *
     * @return
     */
    public ObjectInputStream getInStream()
    {
        return in;
    }
    
    public Server getServer()
    {
        return parent;
    }
    
     public static ClientConnection makeClientThread(Socket socket, long id, Server parent) throws SocketException, IOException
     {
         ClientConnection cThread = new ClientConnection(socket, id, parent);
         cThread.thread = new Thread(cThread);
         cThread.setIn(new ObjectInputStream(socket.getInputStream()));
         cThread.setOut(new ObjectOutputStream(socket.getOutputStream()));
         return cThread;
     }

} // class
