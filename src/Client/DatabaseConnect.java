/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

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
        
    public static void main(String[] args) throws ClassNotFoundException
    {    
        Connection con = null; 
        try 
        {
           
            con = DriverManager.getConnection("jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3", "mbbx9mg3", "Fincherz+2013");
            PreparedStatement selectAll = null;
            String select = "SELECT * FROM 3YP_ITEMS";
            selectAll = con.prepareStatement(select);
            selectAll.executeQuery();
            ResultSet x = selectAll.getResultSet();
         
            while(x.next())
            {
                System.out.println(x.getString("NAME"));
                
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    
    } // main
} // class
