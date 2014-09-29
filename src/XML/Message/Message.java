/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XML.Message;

import java.net.InetAddress;

/**
 *
 * @author Goldy
 */
public abstract class Message 
{
    public Message(){
        
    }
    private InetAddress fromAddress;
    private InetAddress toAddress;
    private String messageID;
    
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
