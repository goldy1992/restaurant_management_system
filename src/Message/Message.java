/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Message;

import java.io.Serializable;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Goldy
 */
public abstract class Message implements Serializable 
{
    
    private final InetAddress fromAddress;
    private final InetAddress toAddress;
    private final String messageID;
    private static final long serialVersionUID = 1L;
    
    /**
     *
     * @param from
     * @param to
     * @param messageID
     */
    public Message(InetAddress from, InetAddress to, String messageID)
    {
        this.fromAddress = from;
        this.toAddress = to;
        this.messageID = messageID;
    } // constructor
    
    /**
     *
     * @return
     */
    public InetAddress getFromAddress()
    {
        return fromAddress;
    } // getFromAddress
    
    /**
     *
     * @return
     */
    public InetAddress getToAddress()
    {
        return toAddress;
    } // getToAddress
    
    /**
     *
     * @return
     */
    public String getMessageID()
    {
        return messageID;
    } // getMessageID 
    
    public String toString()
    {
        return "TO: " + toAddress.toString()
        + "\nFROM: " + fromAddress.toString()
        + "\nMESSAGE ID: " + messageID + "\n";
    }
    
        /**
     *
     * @return
     */
    public static String generateRequestID()
   {
      String request_ID;
      Random random = new Random();
      int x = random.nextInt();
      request_ID = "" + x;
      Date currentDate = new Date();
      Timestamp t = new Timestamp(currentDate.getTime());
      request_ID = request_ID + t;
      return request_ID;
   } // generateRequestID
}
