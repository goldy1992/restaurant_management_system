package com.mike.client.frontend;

import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Request.RegisterClientRequest.ClientType;
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
