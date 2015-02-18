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
        if (evntNfn instanceof NewItemNfn)
        {
            NewItemNfn newItemMessage = (NewItemNfn)evntNfn;
            debugGUI.addMessage(newItemMessage);
        } // if
    }
    

    public enum Type 
    {
        BAR, KITCHEN
    }
    
    private Type type;
   
    public OutputClient()
    {
        super();
    } // constructor
    
    public Type getType()
    {
        return type;
    }
    
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
        if (client.getType() == OutputClient.Type.BAR) 
            client.debugGUI.setTitle("Bar Client Output");
        else if (client.getType() == OutputClient.Type.KITCHEN)
            client.debugGUI.setTitle("Kitchen Client Output"); 
    
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
                                client.getOutputStream().reset();
                client.getOutputStream().writeObject(rKitchenReq);
                                client.getOutputStream().reset();
                System.out.println("sent kitch req");
            } // if
            else
            {
                RegisterClientRequest rBarRequest = new RegisterClientRequest(
                    InetAddress.getByName(
                        client.client.getLocalAddress().getHostName()),
                    InetAddress.getByName(client.serverAddress.getHostName()),
                    Message.generateRequestID(),
                    Request.RequestType.REGISTER_BAR);
                client.getOutputStream().reset();              
                client.getOutputStream().writeObject(rBarRequest);
                client.getOutputStream().reset();
                              System.out.println("sent bar req");
                
            } // else            
       } // try
       catch(IOException ex)
       {
            Logger.getLogger(OutputClient.class.getName()).log(Level.SEVERE, null, ex);         
       }
    } // main
    
    
} // class

    

