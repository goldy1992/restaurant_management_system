/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Message.Message;
import Message.Request.NumOfTablesRequest;
import Message.Request.Request;
import Message.Request.TableStatusRequest;
import Message.Response.NumOfTablesResponse;
import Message.Response.Response;
import Message.Response.TableStatusResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Goldy
 */
public class ClientCommunicationThread implements Runnable
{
    private final Socket socket;
    public final Thread thread;
    public long id;
    
    public ClientCommunicationThread(Socket socket, long id)
    {
        this.socket = socket;
        this.id = id;
        this.thread = new Thread(this);
    } // constructor
    
    @Override
    public void run()
    {
        System.out.println("got here");
        if (socket != null)
        {
            System.out.println(socket);
            try
            (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());    
            )
            {
                System.out.println("read message");
                               
                while(true)
                {
                    Message request = (Message) in.readObject();
                
                    if (request instanceof Request)
                    {
                        Response response = null;  
                        if (request instanceof TableStatusRequest)
                            response = new TableStatusResponse((TableStatusRequest)request);
                        else if (request instanceof NumOfTablesRequest)
                            response = new NumOfTablesResponse((NumOfTablesRequest)request);
                        System.out.println("reponse\n" + request);

                        response.parse();
                        out.writeObject(response);
                    } // the respose if statement
                
 
                    
                System.out.println("returned");
                }
                
            } // try            // try            // try            // try           
            catch(IOException e)
            {
                System.out.println("Failed to set up buffers" + "\n");
                e.printStackTrace();
            } 
            catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientCommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*catch (XMLStreamException e) 
            {
                e.printStackTrace();
            }*/
        }
        while (true);
    } // run
    
}
