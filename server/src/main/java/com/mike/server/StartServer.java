/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mike.message.Table;
import com.mike.server.database.InitialiseDatabase;
import com.mike.server.database.tables.FOOD_OR_DRINK;
import com.mike.server.database.tables.ITEMS;

/**
 * @author Mike
 */
public class StartServer {
    
    private static final int PORT_NUMBER = 11000;
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
    
    } // main

    
}
