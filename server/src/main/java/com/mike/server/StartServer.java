/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import com.mike.message.Table;
import com.mike.server.database.InitialiseDatabase;
import org.apache.log4j.Logger;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Mike
 */
public class StartServer {

    private final static Logger logger = Logger.getLogger(StartServer.class);
    private static final int NUM_OF_TABLES = 44;

    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, SQLException {
        AbstractApplicationContext integrationContext = new ClassPathXmlApplicationContext("/server-context.xml", StartServer.class);
        Server server = (Server) integrationContext.getBean("server");
        InitialiseDatabase initDb = (InitialiseDatabase) integrationContext.getBean("initialiseDatabase");
        initDb.init();
        for (int i = 1; i <= NUM_OF_TABLES; i++) {
        	server.getTables().put(i, new Table(i));
        }
        if (args.length < 1) {
            logger.info("Startup Complete");
        } else {
            logger.info(args[0]);
        }

        DatabaseManagerSwing.main(new String[] { "--url","jdbc:hsqldb:mem:dataSource", "--user", "root", "--password", "root123", "--noexit" });
        boolean exit = false;

        while (!exit) {
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            if (input.trim().toLowerCase().equals("exit")) {
                exit = true;
            }
        }

        integrationContext.close();
        integrationContext.destroy();


    } // main

    
}
