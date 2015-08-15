package Server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Mike
 */
public class ServerRunThread extends Thread 
{
    private ServerSocket socket;
    private boolean socketListening;
    private Server server;
    
    public void run()
    {
       try
        {
            socketListening = true;
            long clientNumber = 0;

            while (socketListening)
            {
                Socket acceptSocket = socket.accept();
                ClientConnection newThread = 
                    ClientConnection.makeClientThread(acceptSocket, 
                                                    clientNumber, 
                                                    server);
                newThread.getThread().start();

                //debugGUI.addText("accept client number " + clientNumber);
                clientNumber++;
            } // while  
        } // try
        catch (BindException e)
        {
            e.printStackTrace();
        } // catch 
        catch (IOException e)
        {
            System.out.println(e);
        } // catch        
    }
    
        
    
    public void end() throws IOException
    {
        socketListening = false;
        socket.close();
    }
    
}
