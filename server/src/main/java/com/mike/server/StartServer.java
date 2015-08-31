/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import com.mike.message.Table;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Mike
 */
public class StartServer {
    
    private static final int PORT_NUMBER = 11000;
    private static final int NUM_OF_TABLES = 44;
    
    public static void main(String[] args) throws IOException {

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Server server = (Server) context.getBean("server");
        Table[] tables = new Table[NUM_OF_TABLES + 1];
        for(int i=1; i < tables.length; i++) {
            tables[i] = (Table) context.getBean("table");
            tables[i].setTableNumber(i);
        }
        try {
            server.setSocket(new ServerSocket(PORT_NUMBER));
            boolean exit = false;
            while(!exit) {
                Scanner sc = new Scanner(System.in);  
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    if (s.equals("exit"))
                        exit = true;
                } // while
            } // while
        } catch(IOException e) {
            
        }
    } // main
    
//    public static Server makeServer(int numOfTables, int portNumber) throws IOException
//    {
//        HashSet<ClientConnection> waiterClient = new HashSet<>();
//        HashSet<ClientConnection> tillClient = new HashSet<>(); 
//        Table[] tables = new Table[numOfTables + 1];
//
//        // creates a thread for each table
//        for (int i = 1; i <= tables.length -1; i++)
//            tables[i] = Table.createTable(i);
//        
//        TableList tableList = new TableList(tables);
//        ServerSocket socket = new ServerSocket(portNumber);
//        Server newServer = new Server(socket, waiterClient, tillClient, tableList);  
//        ServerRunThread serverThread = new ServerRunThread();
//        
//        return newServer;
//    } // makeServer

    /**
     * @return the PORT_NUMBER
     */
    public static int getPORT_NUMBER() {
        return PORT_NUMBER;
    }
    
}
