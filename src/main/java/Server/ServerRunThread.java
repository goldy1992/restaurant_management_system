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
    
    @Override
    public void run()
    {
       try
        {
            setSocketListening(true);
            long clientNumber = 0;

            while (isSocketListening())
            {
                Socket acceptSocket = getSocket().accept();
                ClientConnection newThread = 
                ClientConnection.makeClientThread(acceptSocket, 
                                                    clientNumber, getServer());
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
        setSocketListening(false);
        getSocket().close();
    }

    /**
     * @return the socket
     */
    public ServerSocket getSocket() {
        return socket;
    }

    /**
     * @param socket the socket to set
     */
    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    /**
     * @return the socketListening
     */
    public boolean isSocketListening() {
        return socketListening;
    }

    /**
     * @param socketListening the socketListening to set
     */
    public void setSocketListening(boolean socketListening) {
        this.socketListening = socketListening;
    }

    /**
     * @return the server
     */
    public Server getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(Server server) {
        this.server = server;
    }
    
}
