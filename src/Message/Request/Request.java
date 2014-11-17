/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Request;

import Message.Message;
import java.net.InetAddress;

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
     */
    public static enum RequestType 
    {

        /**
         *  Request the status of a table (FREE, OCCUPIED, IN USE) etc.
         */
        TABLE_STATUS,

        /**
         *  Request the number of tables currently in use.
         */
        NUM_OF_TABLES,

        /**
         *  Requests permission to leave.
         */
        LEAVE,
        
        /**
         *  Requests the current tab from the table server.
         */
        TAB
    }
    
    /**
     *
     */
    public RequestType type;
    
    /**
     *
     * @param from
     * @param to
     * @param messageID
     * @param type
     */
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
