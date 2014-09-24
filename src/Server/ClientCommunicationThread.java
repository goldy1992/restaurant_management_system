/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import XML.Message.Message;
import XML.Message.Request.Request;
import XML.Message.Request.TableStatusRequest;
import XML.XMLMessageParse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
                PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = 
                  new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));        
            )
            {
                System.out.println("read message");
                XMLMessageParse newMessage = new XMLMessageParse(in);
                Message request = newMessage.parse();
                
                if (request instanceof Request)
                {
                    
                } // the respose if statement
                    
                System.out.println("returned");
                while(true);
            }           
            catch(IOException e)
            {
                System.out.println("Failed to set up buffers" + "\n" + e);
            }
            catch (XMLStreamException e) 
            {
                e.printStackTrace();
            }
        }
        while (true);
    } // run
    
}
