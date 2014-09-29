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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
   public static String generateRequestID()
   {
      String request_ID;
      Random random = new Random();
      int x = random.nextInt();
      request_ID = "" + x;
      Date currentDate = new Date();
      Timestamp t = new Timestamp(currentDate.getTime());
      request_ID = request_ID + t;
      return request_ID;
   } // generateRequestID
    
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
                //PrintWriter out =
                  //  new PrintWriter(client.getOutputStream(), true);
                ObjectOutputStream out = new ObjectOutputStream(MyClient.client.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());        
            )
            {
                //XMLWriteRequest request = new XMLWriteRequest(out);

                ArrayList<Integer> tables = new ArrayList<>();
                for(int  i = 0 ; i < MyServer.NUM_OF_TABLES; i++ )
                {
                 
                    tables.add(i + 1);
                }
                TableStatusRequest request = new TableStatusRequest(InetAddress.getByName(client.getLocalAddress().getHostName()),
                                                                                InetAddress.getByName(serverAddress.getHostName()),
                                                                                generateRequestID(),
                                                                                Request.RequestType.TABLE_STATUS,
                                                                                tables);
                
                System.out.println(request);
                

                out.writeObject(request);
                Object response = in.readObject();
                
                if (response instanceof Response)
                {
                    if (response instanceof TableStatusResponse)
                    {
                        System.out.println(response);

                    }
                    
                }
                


                
                while(true);
            } // try
            catch(IOException e)
            {
                System.out.println("Failed to set up buffers");
            } 
            catch (ClassNotFoundException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*catch (XMLStreamException e) 
            {
                e.printStackTrace();
            }*/
            
        } // if`    
    } // main
} // MyClientSocketClass
