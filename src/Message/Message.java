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
    
    public Message(InetAddress from, InetAddress to, String messageID)
    {
        this.fromAddress = from;
        this.toAddress = to;
        this.messageID = messageID;
    } // constructor
    
    public InetAddress getFromAddress()
    {
        return fromAddress;
    } // getFromAddress
    
    public InetAddress getToAddress()
    {
        return toAddress;
    } // getToAddress
    
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
