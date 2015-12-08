/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import java.io.IOException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mike.message.Table;

/**
 * @author Mike
 */
public class StartServer {
    
    private static final int PORT_NUMBER = 11000;
    private static final int NUM_OF_TABLES = 44;
    
    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
        AbstractApplicationContext integrationContext = new ClassPathXmlApplicationContext("/server-context.xml", StartServer.class);
        Server server = (Server) integrationContext.getBean("server");
        
        for (int i = 1; i <= NUM_OF_TABLES; i++) {
        	server.getTables().put(i, new Table(i));
        }
    
    } // main

    
}
