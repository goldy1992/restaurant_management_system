/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Request;

import Message.Message;
import java.io.Serializable;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author mbbx9mg3
 */
public abstract class Request extends Message
{

    public Object getTableList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static enum RequestType 
    {
        TABLE_STATUS, NUM_OF_TABLES
    }
    
    public RequestType type;
    
    public Request(InetAddress from, 
                          InetAddress to, 
                          String messageID, 
                          RequestType type)
    {
       super(from, to, messageID);
       this.type = type;
    }
    
    public String toString()
    {
        return super.toString() + "TYPE: Request\n";
    }
    
    
}
