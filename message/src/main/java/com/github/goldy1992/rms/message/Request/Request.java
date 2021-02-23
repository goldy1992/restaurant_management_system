/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goldy1992.rms.message.Request;

import com.github.goldy1992.rms.message.Message;


/**
 *
 * @author mbbx9mg3
 */
public abstract class Request extends Message {

    public Object getTableList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Request()
    {
        super();
    }
    
    public String toString()
    {
        return "TYPE: Request\n";
    }
    
}
