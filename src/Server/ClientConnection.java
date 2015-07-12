package Server;

import Message.*;
import Client.OutputGUI;
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
 */
public class ClientConnection implements Runnable
{
    private final MyServer parent;
    private final Socket socket;
    private Thread thread;
    public long id;
    private ObjectOutputStream out;
    private ObjectInputStream in;   
    public boolean isRunning;
    public OutputGUI gui;
    
    /**
     *
     * @param socket
     * @param id
     * @param parent
     * @throws java.net.SocketException
     */
    public ClientConnection(Socket socket, long id, MyServer parent) throws SocketException
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
   
    @Override
    public void run()
    {
        if (socket != null)
        {
            Message message = null;
            try
            {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());    
                              
                while(isRunning)
                {
                    message = (Message) in.readObject();                        
                    ParseMessage parse = new ParseMessage(message, this);
                    parse.parse();
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
            } // catch
            catch (ClassNotFoundException  ex) 
            {
                Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            } // catch             // catch             // catch             // catch            
        } // if socket == null
    } // run
   
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
    
    public MyServer getServer()
    {
        return parent;
    }
    
     public static ClientConnection makeClientThread(Socket socket, long id, MyServer parent) throws SocketException
     {
         ClientConnection cThread = new ClientConnection(socket, id, parent);
         cThread.thread = new Thread(cThread);
         return cThread;
     }

} // class
