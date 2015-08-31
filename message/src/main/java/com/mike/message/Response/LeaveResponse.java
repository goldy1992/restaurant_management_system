/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.Response;

import com.mike.message.Request.LeaveRequest;
import com.mike.message.Request.Request;

/**
 *
 * @author mbbx9mg3
 */
public class LeaveResponse extends Response
{

    private boolean permissionGranted = false;
    /**
     *
     * @param request
     */
    
    public LeaveResponse(LeaveRequest request)
    {
        super(request);

    } // contructor
    
    public boolean hasPermission()
    {
        return permissionGranted;
    }

    public void setPermission(boolean permission)
    {
        this.permissionGranted = permission;
    }
    
}
