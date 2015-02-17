/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Response;

import Client.WaiterClient;
import Message.Request.RegisterClientRequest;
import Message.Request.Request;
import static Message.Request.Request.RequestType.*;
import Server.MyServer;

/**
 *
 * @author mbbx9mg3
 */
public class RegisterClientResponse extends Response
{
    private boolean permissionGranted;
    
    public RegisterClientResponse(Request request)
    {
        super(request);
    } // constructor

    @Override
    public void parse() 
    {
        
        if(hasParsedResponse())
            return;
        
        RegisterClientRequest req = (RegisterClientRequest)getRequest();
       // MyServer.debugGUI.addText("not already parsed");
        
        if (req.type == REGISTER_BAR)
        {
            permissionGranted = (MyServer.getBarClient() == null);
            //MyServer.debugGUI.addText("permission granted: " + permissionGranted 
                //+ "\n" + MyServer.getBarClient());
        }
        else if (req.type == REGISTER_KITCHEN) 
        {
            permissionGranted = (MyServer.getKitchenClient() == null);
            //MyServer.debugGUI.addText("permission granted: " + permissionGranted 
                               // + "\n" + MyServer.getKitchenClient());
        }
        else permissionGranted = true;
        
        this.parsedResponse = true;
        
    }

    @Override
    public void onReceiving() 
    {

    } // onReceiving
    
    public boolean hasPermission()
    {
        return permissionGranted;
    } // hasPermission
    
    public void setPermission(boolean permission)
    {
        permissionGranted = permission;
    } // setPermission
} // class
