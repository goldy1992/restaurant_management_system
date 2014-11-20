/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Response;

import Bar.BarClient;
import Message.Request.RegisterClientRequest;
import Message.Request.Request;
import static Message.Request.Request.RequestType.REGISTER_BAR;
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
        System.out.println("not already parsed");
        if (req.type == REGISTER_BAR)
        permissionGranted = (MyServer.getBarClient() == null);
        else permissionGranted = (MyServer.getKitchenClient() == null);
        System.out.println("permission granted: " + permissionGranted + "\n" + MyServer.getBarClient());
        
        this.parsedResponse = true;
        
    }

    @Override
    public void onReceiving() 
    {
        if (!hasPermission())
        {
            BarClient.setRunning(false);
            System.out.println("A bar client already exists!");
        } // if
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
