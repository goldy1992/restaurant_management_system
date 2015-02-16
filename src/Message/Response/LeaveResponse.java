/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Response;

import Message.Request.Request;

/**
 *
 * @author mbbx9mg3
 */
public class LeaveResponse extends Response
{

    
    /**
     *
     * @param request
     */
    public LeaveResponse(Request request)
    {
        super(request);

    } // contructor
    
    /**
     *
     */
    @Override
    public void parse()
    {

    }


    @Override
    public void onReceiving() 
    {
        //MyClient.debugGUI.addText("reached exit");
        System.exit(0);
    }
    
}
