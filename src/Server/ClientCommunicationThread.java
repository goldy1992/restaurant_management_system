/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Client.MyClient;
import static Client.MyClient.client;
import XML.Message.Message;
import XML.Message.Request.Request;
import XML.Message.Request.TableStatusRequest;
import XML.Message.Response.Response;
import XML.Message.Response.TableStatusResponse;
import XML.XMLMessageParse;
import XML.XMLWriteResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLStreamException;

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
              /* PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = 
                  new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));     */
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());    

            )
            {
                System.out.println("read message");
                //XMLMessageParse newMessage = new XMLMessageParse(in);
                                    Message request = (Message) in.readObject();
                                    System.out.println("gothere");
                //Message request = newMessage.parse();
                
                if (request instanceof Request)
                {
                    Response response = null;  
                    if (request instanceof TableStatusRequest)
                    {
                        System.out.println("reponse");
                        response = new TableStatusResponse((TableStatusRequest)request);
                        response.parse();
                    }
                    //XMLWriteResponse responseMessage = new XMLWriteResponse(out, response);
                    //responseMessage.tableStatusRequest();
                    out.writeObject(response);
                } // the respose if statement
                
 
                    
                System.out.println("returned");
                while(true);
            }           
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
