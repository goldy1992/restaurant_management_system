/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.EventNotification.EventNotification;
import Message.EventNotification.NewItemNfn;
import Message.Message;
import Message.Request.RegisterClientRequest;
import Message.Request.Request;
import Message.Response.RegisterClientResponse;
import Message.Response.Response;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbbx9mg3
 */
public class OutputClient extends Client implements Runnable
{

    @Override
    public void parseResponse(Response response) 
        throws IOException, ClassNotFoundException 
    {
        if (response instanceof RegisterClientResponse)
        {
            RegisterClientResponse rResponse = (RegisterClientResponse)response;
            rResponse.onReceiving();
            RegisterClientRequest req = (RegisterClientRequest)rResponse.getRequest();
            if (!rResponse.hasPermission())
            {
                debugGUI.addText("A client already exists!");
                System.exit(0);                               
            } // if
                            
            else debugGUI.addText("Client successfully registered as: " + req);
        } // if   
    }

    @Override
    public void parseEventNotification(EventNotification evntNfn) 
        throws IOException, ClassNotFoundException 
    {
        NewItemNfn newItemMessage = (NewItemNfn)evntNfn;
        debugGUI.addMessage(newItemMessage);
    }
    

    public enum Type 
    {
        BAR, KITCHEN
    }
    
    private Type type;
   
    public OutputClient()
    {
        super();
        if (type == Type.KITCHEN)
            debugGUI.setTitle("Kitchen Client");
        else
            debugGUI.setTitle("Bar Client");
        debugGUI.setVisible(true);
    } // constructor
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {
        Type type = null;
     
        switch(args[0])
        {
            case "bar": type = Type.BAR; break;
            case "kitchen": type = Type.KITCHEN; break;
            default: type = Type.BAR; break;
        }    
        OutputClient client = Client.makeClient(OutputClient.class);
        client.type = type;
       
        try
        {                  
            if (type == Type.KITCHEN)
            {
                RegisterClientRequest rKitchenReq = new RegisterClientRequest(
                    InetAddress.getByName(
                        client.client.getLocalAddress().getHostName()),
                    InetAddress.getByName( client.serverAddress.getHostName()),
                    Message.generateRequestID(),
                    Request.RequestType.REGISTER_KITCHEN);         
                client.getOutputStream().writeObject(rKitchenReq);
            } // if
            else
            {
                RegisterClientRequest rBarRequest = new RegisterClientRequest(
                    InetAddress.getByName(
                        client.client.getLocalAddress().getHostName()),
                    InetAddress.getByName(client.serverAddress.getHostName()),
                    Message.generateRequestID(),
                    Request.RequestType.REGISTER_BAR);
            
                client.getOutputStream().writeObject(rBarRequest);
                
            } // else            
       } // try
       catch(IOException ex)
       {
            Logger.getLogger(OutputClient.class.getName()).log(Level.SEVERE, null, ex);         
       }
    } // main
    
    
} // class

    

