/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.EventNotification.EventNotification;
import Message.EventNotification.NewItemNfn;
import Message.Request.RegisterClientRequest;
import static Message.Request.RegisterClientRequest.ClientType.BAR;
import static Message.Request.RegisterClientRequest.ClientType.KITCHEN;
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
        System.out.println("parse response");
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
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args)  throws InterruptedException
    {

        OutputClient client = null;
        try
        {
            switch(args[0])
            {
                case "bar": System.out.println("making bar"); 
                client = (OutputClient)Client.makeClient(BAR); 
                System.out.println("made bar " + client);break;
                case "kitchen": System.out.println("making kitchen"); 
                client = (OutputClient)Client.makeClient(KITCHEN); 
                System.out.println("made kitchen " + client); break;
                default: System.out.println("invalid argument"); System.exit(0); 
            }
            System.out.println("end of switch");

        client.debugGUI.setVisible(true);
        System.out.println("got here");
        while (true);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
           
    } // main
    
    
} // class

    

