/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.Response;

import com.mike.message.Request.Request;
import com.mike.message.Message;


/**
 *
 * @author mbbx9mg3
 */
public abstract class Response extends Message
{    
    private Request request = null;
    private boolean parsedResponse;

    /**
     *
     * @param request
     */
    public Response(Request request)
    {
       super(request.getToAddress(), request.getFromAddress());
       this.request = request;
       parsedResponse = false;
    } // constructor
    
    /**
     *
     * @return
     */
    public Request getRequest()
    {
        return request;
    } // getRequest
 
    /**
     *
     * @return
     */
    public boolean hasParsedResponse()
    {
        return parsedResponse;
    }
    
    public void setParsed(boolean parsed)
    {
        this.parsedResponse = parsed;
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "TYPE: Response\n";
    }
    

    
} // class
