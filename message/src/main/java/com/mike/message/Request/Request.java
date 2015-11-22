/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.Request;

import com.mike.message.Message;


/**
 *
 * @author mbbx9mg3
 */
public abstract class Request extends Message
{

    /**
     *
     * @return
     */
    public Object getTableList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *
     * @param from
     * @param to
     * @param messageID
     * @param type
     */
    public Request()
    {
        super();
    }
    
    public String toString()
    {
        return "TYPE: Request\n";
    }
    
}
