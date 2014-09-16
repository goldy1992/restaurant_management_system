/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XML;

import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Goldy
 */
public class XMLMessageParse 
{
    BufferedReader inStream;
    XMLInputFactory factory;
    XMLEventFactory  eventFactory;
    XMLEventReader reader;
    XMLEvent event;    
    
    /**
     *
     * @param stream
     */
    public XMLMessageParse(BufferedReader stream)
    {
        inStream = stream;
    }
    
    public void parse() throws XMLStreamException, UnknownHostException
    {
        InetAddress fromAddress;
        InetAddress toAddress;
        String messageID;
        
        factory      = XMLInputFactory.newInstance();
        eventFactory = XMLEventFactory.newInstance();
        reader = factory.createXMLEventReader(inStream);

        int i = 0 ;
        while (reader.hasNext())
        {
            event = reader.nextEvent();
           // System.out.println(i + ":  " + event.getEventType() + "   " + event);
            i++;
                                
            switch(event.getEventType())
            {
                case XMLEvent.START_ELEMENT:
                    if (event.asStartElement().getName().getLocalPart().equals("to"))
                    {
                       event = reader.nextEvent();
                       toAddress= InetAddress.getByName(event.asCharacters().getData());
                       System.out.println("to: " + toAddress);
                    } // if
                    else if (event.asStartElement().getName().getLocalPart().equals("from"))
                    {
                       event = reader.nextEvent();
                       fromAddress= InetAddress.getByName(event.asCharacters().getData());
                       System.out.println("from: " + fromAddress);
                    } // if
                    else if (event.asStartElement().getName().getLocalPart().equals("id"))
                    {
                       event = reader.nextEvent();
                       messageID = event.asCharacters().getData();
                       System.out.println("id: " + messageID);
                    } // if
                    else if (event.asStartElement().getName().getLocalPart().equals("body"))
                    {
                       event = reader.nextEvent();
                       if (event.asStartElement().getName().getLocalPart().equals("type"))
                       {
                           event = reader.nextEvent();
                           if (event.asCharacters().getData().equals("TABLE_STATUS"));
                           System.out.println("valid message");
                       } // inner if
                       else
                       {
                           System.out.println("invalid message no type after body");
                       } // else
                    } // if
                    break;
            } // switch
        } // while
    } // parse
    
    private void parseTableStatus()
    {
        
    } // parseTableStatus
    
} // XMLMessageParse
