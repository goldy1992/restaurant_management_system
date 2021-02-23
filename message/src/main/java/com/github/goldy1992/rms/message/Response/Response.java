/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goldy1992.rms.message.Response;

import com.github.goldy1992.rms.message.Message;
import com.github.goldy1992.rms.message.Request.Request;


/**
 *
 * @author mbbx9mg3
 */
public abstract class Response extends Message {

	private Request request = null;
    private boolean parsedResponse;

    /**
     *
     * @param request
     */
    public Response(Request request) {
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
        return "TYPE: Response\n";
    }

} // class
