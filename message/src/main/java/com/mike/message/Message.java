package com.mike.message;

import java.io.Serializable;
import java.net.InetAddress;
import org.springframework.messaging.MessageHeaders;
import sun.nio.cs.ext.TIS_620;



/**
 *
 * @author Goldy
 */
public abstract class Message implements Uniqueness, Serializable
{
    private MessageHeaders messageHeaders;
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
    public Message(InetAddress from, InetAddress to)
    {
        this.fromAddress = from;
        this.toAddress = to;
        this.messageID = generateUniqueID();
    } // constructor
    
    public Message(){
    fromAddress=null;toAddress=null;messageID=null;
            }
    

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
