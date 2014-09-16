/* USEFUL CODE
            try
            (

                PrintWriter out =
                    new PrintWriter(acceptSocket.getOutputStream(), true);                   
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(acceptSocket.getInputStream()));   
            ) // try open bracket
            {
                String inputLine;
                if ((inputLine = in.readLine()) != null) 
                {
                    
                    out.println(LocalDateTime.now(Clock.systemDefaultZone()));
                } // if

*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */

public class MyServer
{
    public static ArrayList<ClientCommunicationThread> clients;

    // the lower bound of the port range
    public static int lowerBoundPortRange = 11000;
    
    public static void main(String[] args)
    {
        
        clients = new ArrayList<>();
        Table[] tables = new Table[31];
        for (int i = 1; i <= 30; i++)
        {
            tables[i] = new Table(i);
            new Thread(tables[i]).start();
        } // for
        
        ServerSocket mySocket;
        boolean socketListening;
        
        try
        {
            System.out.println("got here");
            mySocket = new ServerSocket(lowerBoundPortRange);
                System.out.println("socket listening");
            socketListening = true;
            System.out.println("Currently listening on port " + lowerBoundPortRange);
            
            long clientNumber = 0;
            
            while (socketListening)
            {

                Socket acceptSocket = mySocket.accept();
                ClientCommunicationThread newThread = new ClientCommunicationThread(acceptSocket, clientNumber);
                newThread.thread.start();
                clients.add(newThread);
                clientNumber++;
            } // while
   
            mySocket.close();
            
        } // try
        catch (BindException e)
        {
            e.printStackTrace();
        } // catch 
        catch (IOException e)
        {
            System.out.println(e);
        } // catch   
        
    } // main
        
} // MySocket class
