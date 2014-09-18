/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML.Message.Request;

import XML.Message.Message;
import java.net.InetAddress;

/**
 *
 * @author mbbx9mg3
 */
public abstract class RequestMessage extends Message 
{
    public static enum RequestType 
    {
        TABLE_STATUS
    }
    
    public RequestType type;
    
    public RequestMessage(InetAddress from, 
                          InetAddress to, 
                          String messageID, 
                          RequestType type)
    {
       super(from, to, messageID);
       this.type = type;
    }
    
}
