/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML.Message.Response;

import XML.Message.Request.Request;
import XML.Message.Message;


/**
 *
 * @author mbbx9mg3
 */
public abstract class Response extends Message 
{    
    private Request request;
    protected boolean parsedResponse;
    public Response(Request request)
    {
       super(request.getToAddress(), request.getFromAddress(), request.getMessageID());
       this.request = request;
       parsedResponse = false;
    } // constructor
    
    public Request getRequest()
    {
        return request;
    } // getRequest
 
    public boolean hasParsedResponse()
    {
        return parsedResponse;
    }
    
    public abstract void parse();
    
} // class
