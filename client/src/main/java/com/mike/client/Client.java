package com.mike.client;

import com.mike.message.Request.LeaveRequest;
import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Response.RegisterClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mbbx9mg3
 */
@Component
public abstract class Client
{     
    private final ClientType type;
    private static final long serialVersionUID = 1L;
       
    public Client(RegisterClientRequest.ClientType type) {
        this.type = type;
    }
    
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
    
    
    public ClientType getType() {
        return type;
    }
    

}
