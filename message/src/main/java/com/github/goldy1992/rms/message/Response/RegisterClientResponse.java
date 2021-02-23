/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goldy1992.rms.message.Response;

import com.github.goldy1992.rms.message.Request.RegisterClientRequest;

/**
 *
 * @author mbbx9mg3
 */
public class RegisterClientResponse extends Response {

    private boolean permissionGranted;
    private final RegisterClientRequest.ClientType clientType;
    
    public RegisterClientResponse(RegisterClientRequest request) {
        super(request);
        clientType = request.getClientType();
    } // constructor

    public boolean hasPermission() { return permissionGranted; } // hasPermission
    public void setPermission(boolean permission) { permissionGranted = permission; } // setPermission
    public RegisterClientRequest.ClientType getClientType() { return clientType; }
} // class
