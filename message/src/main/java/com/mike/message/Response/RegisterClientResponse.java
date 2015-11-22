/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.Response;

import com.mike.message.Message;
import com.mike.message.Request.RegisterClientRequest;
import org.springframework.messaging.MessageHeaders;

/**
 *
 * @author mbbx9mg3
 */
public class RegisterClientResponse extends Response
{
    private boolean permissionGranted;
    private final RegisterClientRequest.ClientType clientType;
    
    public RegisterClientResponse(RegisterClientRequest request)
    {
        super(request);
        clientType = request.getClientType();
    } // constructor

    
    public boolean hasPermission()
    {
        return permissionGranted;
    } // hasPermission
    
    public void setPermission(boolean permission)
    {
        permissionGranted = permission;
    } // setPermission
    
    public RegisterClientRequest.ClientType getClientType()
    {
        return clientType;
    }

    @Override
    public Message getPayload() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MessageHeaders getHeaders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
} // class
