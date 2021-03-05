/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goldy1992.rms.server;

import com.github.goldy1992.rms.message.Table;
import com.github.goldy1992.rms.server.database.InitialiseDatabase;
import org.apache.log4j.Logger;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Mike
 */
@SpringBootApplication
@ImportResource("server-context.xml")
public class StartServer implements CommandLineRunner {


    private final static Logger logger = Logger.getLogger(StartServer.class);
    private static final int NUM_OF_TABLES = 44;

    @Autowired
    private Server server;

    @Autowired
    private InitialiseDatabase initDb;

    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, SQLException {
     SpringApplication.run(StartServer.class, args);



    } // main


    @Override
    public void run(String... args) throws Exception {
        initDb.init();
        for (int i = 1; i <= NUM_OF_TABLES; i++) {
            server.getTables().put(i, new Table(i));
        }
        if (args.length < 1) {
            logger.info("Startup Complete");
        } else {
            logger.info(args[0]);
        }

    }
}
