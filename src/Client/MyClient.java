/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import Server.MyServer;
import XML.Message.Message;
import XML.Message.Request.Request;
import XML.Message.Request.TableStatusRequest;
import XML.Message.Response.Response;
import XML.Message.Response.TableStatusResponse;
import XML.XMLMessageParse;
import XML.XMLWriteRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.xml.stream.XMLStreamException;


/**
 *
 * @author Goldy
 */
public class MyClient 
{
    public static InetAddress serverAddress; 
    public static int serverPort;  
    public static Socket client;
    
    public static void main(String[] args)
    {
        
        try
        {
            serverAddress = InetAddress.getByName(null);
            serverPort = MyServer.LB_PORT_RANGE;
            System.out.println(serverPort);
            client = new Socket(serverAddress, serverPort);
            System.out.println(client);
        } // try
        catch (IOException e)
        {
            System.out.println("Could not connect to server");
        } // catch
        
        if (client != null)
        {
            try
            (
                PrintWriter out =
                    new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = 
                  new BufferedReader(
                    new InputStreamReader(client.getInputStream()));        
            )
            {
                XMLWriteRequest request = new XMLWriteRequest(out);

                int[] tables = new int[30];
                        for(int  i = 0 ; i < tables.length; i++ )
                            tables[i] = i+1; 
                Request request = new Request();
                request.tableStatusRequest(tables);
                                System.out.println("got here1");
                XMLMessageParse responseMessage = new XMLMessageParse(in, request);
                
                Message response = responseMessage.parse();
                
                if (response instanceof Response)
                {
                    if (response instanceof TableStatusResponse)
                    {
                        System.out.println("reponse");

                    }
                    
                }
                


                
                while(true);
            } // try
            catch(IOException e)
            {
                System.out.println("Failed to set up buffers");
            }
            catch (XMLStreamException e) 
            {
                e.printStackTrace();
            }
            
        } // if`    
    } // main
} // MyClientSocketClass
