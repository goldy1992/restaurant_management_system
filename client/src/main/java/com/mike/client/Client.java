package com.mike.client;

import com.mike.message.EventNotification.EventNotification;
import com.mike.message.Message;
import com.mike.message.Request.LeaveRequest;
import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Response.LeaveResponse;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.Response;
import com.mike.server.StartServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

/**
 *
 * @author mbbx9mg3
 */
@Component
public abstract class Client
{
    @Autowired
    public MessageSender messageSender;
    
    
    private final ClientType type;
    private static final long serialVersionUID = 1L;
       
    public Client(RegisterClientRequest.ClientType type)
    {
        this.type = type;
    }
    
    @ServiceActivator(inputChannel="registerClientResponseChannel")
    private void registerClientResponse(RegisterClientResponse resp)
    {
        System.out.println("parse register client response");
        RegisterClientResponse rResponse = (RegisterClientResponse)resp;
        RegisterClientRequest req = (RegisterClientRequest)rResponse.getRequest();
            
        if (!rResponse.hasPermission())
        {
           // debugGUI.addText("A client already exists!");
            System.exit(0);                               
        } // if
        else { 
        	
        	System.out.println("Client successfully registered as: " + req);        
        }
    } // regClientResp
      
   
   
    public String sortTimeSyntax(int time)
    {        
        if (time == 0) return "00";
        else if (time > 0 && time <= 9)
            return "0" + time;
        else return time + "";
        
    } // sortTimeSyntax

    public String timeToString(int hours, int minutes)
    {
        return sortTimeSyntax(hours) + ":" + sortTimeSyntax(minutes);
    }
   
    
    public static Client makeClient(ClientType clientType)
    {
        Client till;

        switch (clientType)
        {
            case WAITER:
            //    till = new WaiterClient(clientType);

                break;
            case TILL:
            //    till = new TillClient(clientType);
                break;       
            default:
            //    till = new OutputClient(clientType);
        }
          //  till.debugGUI = OutputGUI.makeGUI(till);        
            //till.responseThread = new Thread(till);
            //till.responseThread.start();
            //till.registerClient();
            return null;
    } // makeClient
    
    
    public ClientType getType()
    {
        return type;
    }
    
    public final boolean registerClient()
    {
        RegisterClientRequest rKitchenReq = new RegisterClientRequest(type);
        messageSender.send(rKitchenReq);
        System.out.println("message sent");
        //return writeMessage(rKitchenReq);
        return true;
    } // registerClient
    
    public final boolean leaveRequest()
    {
        LeaveRequest leaveRequest = new LeaveRequest();
       messageSender.send(leaveRequest);
        return true;
    } // registerClient
    
   
    
    public void setMessageSender(MessageSender messageSender){ this.messageSender = messageSender; }

}
