/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mbbx9mg3
 */
public class DatabaseConnect 
{
        
    /**
     *
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException
    {    
        Connection con = null; 
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://sql4.freemysqlhosting.net:3306/sql482884", "sql482884", "aN9*kG1!");           
 //           con = DriverManager.getConnection("jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3", "mbbx9mg3", "Fincherz+2013");
            PreparedStatement numberOfButtonsQuery = null;
            String query = "SELECT COUNT(ID) FROM 3YP_POS_IN_MENU WHERE LOCATION = 'MAIN_PAGE'";
            numberOfButtonsQuery = con.prepareStatement(query);
            numberOfButtonsQuery.executeQuery();
            ResultSet x = numberOfButtonsQuery.getResultSet();
            int result = -1;
            if (x.next())
                result = x.getInt(1);
            System.out.println("number of results: " + result);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    
    } // main
} // class
