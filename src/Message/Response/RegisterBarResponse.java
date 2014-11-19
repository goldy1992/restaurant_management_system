/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Response;

import Bar.BarClient;
import Message.Request.Request;
import Server.MyServer;

/**
 *
 * @author mbbx9mg3
 */
public class RegisterBarResponse extends Response
{
    private boolean permissionGranted;
    
    public RegisterBarResponse(Request request)
    {
        super(request);
    } // constructor

    @Override
    public void parse() 
    {
        
        if(hasParsedResponse())
            return;
        permissionGranted = (MyServer.getBarClient() == null);
        
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
