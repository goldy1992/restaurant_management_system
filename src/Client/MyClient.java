/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import Server.MyServer;
import XML.XMLRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


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
            serverPort = MyServer.lowerBoundPortRange;
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
                XMLRequest request = new XMLRequest(out);
                int[] tables = new int[30];
                        for(int  i = 1 ; i < tables.length; i++ )
                            tables[i] = i; 
                request.tableStatusRequest(tables);

                
                while(true);
            } // try
            catch(IOException e)
            {
                System.out.println("Failed to set up buffers");
            }
            
        } // if`    
    } // main
} // MyClientSocketClass
