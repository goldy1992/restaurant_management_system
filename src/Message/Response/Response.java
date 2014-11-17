/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Response;

import Message.Request.Request;
import Message.Message;


/**
 *
 * @author mbbx9mg3
 */
public abstract class Response extends Message implements DoWhenReceived
{    
    private Request request = null;

    /**
     *
     */
    protected boolean parsedResponse;

    /**
     *
     * @param request
     */
    public Response(Request request)
    {
       super(request.getToAddress(), request.getFromAddress(), request.getMessageID());
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
    
    /**
     *
     */
    public abstract void parse();
    
    public String toString()
    {
        return super.toString() + "TYPE: Response\n";
    }
    
    /**
     * implements the method to be called when the message is received.
     */
    @Override
    public abstract void onReceiving();
    
} // class
