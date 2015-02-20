/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.EventNotification.*;
import Message.Request.RegisterClientRequest;
import Message.Response.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbbx9mg3
 */
public class TillClient extends Client implements Runnable
{
    
    public TillClient(RegisterClientRequest.ClientType  type) throws IOException
    {
        super(type);
    } // constructor
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {   
        TillClient myClient = null;
        try 
        {
            myClient = Client.makeClient(RegisterClientRequest.ClientType.TILL);
            TillGUI gui = new TillGUI(myClient);
            gui.setTitle("Till");
            gui.setVisible(true);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(TillClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("end of till client");
    } // main
      
    /**
     *
     * @param response
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */  
    @Override
    public void parseResponse(Response response) throws IOException, ClassNotFoundException {
        super.parseResponse(response);
        
        if (response instanceof RegisterClientResponse)
        {
                    System.out.println("parse register client response");
            RegisterClientResponse rResponse = (RegisterClientResponse)response;
            RegisterClientRequest req = (RegisterClientRequest)rResponse.getRequest();
            
            if (!rResponse.hasPermission())
            {
                debugGUI.addText("A client already exists!");
                System.exit(0);                               
            } // if
        
            else System.out.println("Client successfully registered as: " + req);
        }
    }

    @Override
    public void parseEventNotification(EventNotification evntNfn) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
} // class

    

