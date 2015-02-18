/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.EventNotification.*;
import Message.Response.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author mbbx9mg3
 */
public class TillClient extends Client implements Runnable
{
    
    public TillClient()
    {
        super();
    } // constructor
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {   
        TillClient myClient = Client.makeClient(TillClient.class);
        TillGUI gui = new TillGUI(myClient);
        gui.setVisible(true);
    } // main
      
    /**
     *
     * @return
     */
    
    @Override
    public void parseResponse(Response response) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void parseEventNotification(EventNotification evntNfn) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
} // class

    

