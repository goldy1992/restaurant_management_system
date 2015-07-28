package Message;

import OtherClasses.Uniqueness;
import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Goldy
 */
public abstract class Message implements Serializable, Uniqueness
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
    public Message(InetAddress from, InetAddress to)
    {
        this.fromAddress = from;
        this.toAddress = to;
        this.messageID = generateUniqueID();
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
