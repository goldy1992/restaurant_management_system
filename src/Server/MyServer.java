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
    // the lower bound of the port range
    public static final int LB_PORT_RANGE = 11000;
    public static final int NUM_OF_TABLES = 30;
    
    private static ArrayList<ClientCommunicationThread> clients;
    private static Table[] tables;
    
    public static void main(String[] args)
    {
        clients = new ArrayList<>();
        tables = new Table[NUM_OF_TABLES + 1];
        
        // creates a thread for each table
        for (int i = 1; i <= NUM_OF_TABLES; i++)
        {
            tables[i] = new Table(i);
            new Thread(tables[i]).start();
        } // for
        
        
        ServerSocket mySocket;
        boolean socketListening;
        
        try
        {
            System.out.println("got here");
            mySocket = new ServerSocket(LB_PORT_RANGE);
                System.out.println("socket listening");
            socketListening = true;
            System.out.println("Currently listening on port " + LB_PORT_RANGE);
            
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
    
    public static Table getTable(int number)
    {
        if (number < 1 || number > NUM_OF_TABLES)
            return null;
        else return tables[number];
    } // table
        
} // MySocket class
