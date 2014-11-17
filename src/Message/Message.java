/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Message;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Goldy
 */
public abstract class Message implements Serializable 
{
    
    private final InetAddress fromAddress;
    private final InetAddress toAddress;
    private final String messageID;
    
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
}
