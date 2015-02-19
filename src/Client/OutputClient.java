/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.EventNotification.EventNotification;
import Message.EventNotification.NewItemNfn;
import Message.Request.RegisterClientRequest;
import Message.Response.RegisterClientResponse;
import Message.Response.Response;
import java.io.IOException;

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
        super.parseResponse(response);
        if (response instanceof RegisterClientResponse)
        {
            RegisterClientResponse rResponse = (RegisterClientResponse)response;
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
        super.parseEventNotification(evntNfn);
        if (evntNfn instanceof NewItemNfn)
        {
            NewItemNfn newItemMessage = (NewItemNfn)evntNfn;
            debugGUI.addMessage(newItemMessage);
        } // if
    }
     
    public OutputClient(RegisterClientRequest.ClientType type) throws IOException 
    {
        super(type);
    } // constructor
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {

        OutputClient client = null;
        try
        {
            switch(args[0])
            {
                case "bar": client = Client.makeClient(RegisterClientRequest.ClientType.BAR); break;
                case "kitchen": client = Client.makeClient(RegisterClientRequest.ClientType.KITCHEN); break;
                default: System.out.println("invalid argument"); System.exit(0); break;
            }
        }
        catch(IOException ex)
        {
            
        }
        System.out.println("got here");

        //
           
    } // main
    
    
} // class

    

