/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XML;

import XML.Message.Message;
import XML.Message.Request.Request;
import XML.Message.Request.TableStatusRequest;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
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
    InetAddress fromAddress;
    InetAddress toAddress;
    String messageID;
    
    /**
     *
     * @param stream
     */
    public XMLMessageParse(BufferedReader stream)
    {
        inStream = stream;
    }
    
    public Message parse() throws XMLStreamException, UnknownHostException
    {       
        factory      = XMLInputFactory.newInstance();
        eventFactory = XMLEventFactory.newInstance();
        reader = factory.createXMLEventReader(inStream);
        
        Message message = null;

        int i = 0 ;
        while (reader.hasNext())
        {
            event = reader.nextEvent();
           // System.out.println(i + ":  " + event.getEventType() + "   " + event);
            i++;
                                
            switch(event.getEventType())
            {
                case XMLEvent.START_ELEMENT:
                    switch (event.asStartElement().getName().getLocalPart()) 
                    {

                        case "to":
                            event = reader.nextEvent();
                            toAddress= InetAddress.getByName(event.asCharacters().getData());
                            System.out.println("to: " + toAddress);
                            break;

                        case "from":
                            event = reader.nextEvent();
                            fromAddress= InetAddress.getByName(event.asCharacters().getData());
                            System.out.println("from: " + fromAddress);
                            break;
      
                        case "id":
                            event = reader.nextEvent();
                            messageID = event.asCharacters().getData();
                            System.out.println("id: " + messageID);
                            break;
 
                        case "body":
                            parseBodyTag(message);
                            break;
                            
                    } // inner switch
                    break;
                case XMLEvent.END_ELEMENT:
                    if (event.asEndElement().getName().getLocalPart().equals("/request")) 
                        return message;

                break;    
                    
            } // switch
        } // while
        

        return message;
    } // parse
    
    private TableStatusRequest parseTableStatus() throws XMLStreamException
    {
        ArrayList<Integer> list = null; 
        int listSize = 0;
        
        // holds the table element
        event = reader.nextEvent();
 
        Iterator<Attribute> x = event.asStartElement().getAttributes();
        while(x.hasNext())
        {
            Attribute a = x.next();
            System.out.println(a.getName().getLocalPart() + " " + a.getValue());
            switch (a.getName().getLocalPart()) 
            {
                case "type":
                    if (a.getValue().equals("Array"))
                    {
                        System.out.println("found array type");
                        list = new ArrayList();
                    }   
                    break;
                case "total":
                    listSize = Integer.parseInt(a.getValue());
                    break;
            }
        } // while
        
        if (list == null)
        {
            System.err.println("Invalid XML message");
            return null;
        } // if
        
            // holds the first value element
            event = reader.nextEvent();
            
            while (event.isStartElement() 
                   && event.asStartElement().getName().getLocalPart().equals("value"))
            {
                // the value
                event = reader.nextEvent();
                list.add(Integer.parseInt(event.asCharacters().getData()));
                event = reader.nextEvent(); // reads end Element
                event = reader.nextEvent(); // reads possibleNext startElement
            } // while
            
            if (list.size() == listSize)
                System.out.println("Everything went well!");

        
        System.out.println(list);
        Request.RequestType requestType = Request.RequestType.TABLE_STATUS;
        
        return new TableStatusRequest(fromAddress, toAddress, 
                                      messageID, requestType, list);
        
    } // parseTableStatus
    
    private Message parseBodyTag(Message message) throws XMLStreamException
    {
        event = reader.nextEvent();
        if (event.asStartElement().getName().getLocalPart().equals("type"))
        {
            event = reader.nextEvent();
            boolean validXML = true;
            if (event.asCharacters().getData().equals("TABLE_STATUS"))
            {
                System.out.println("valid message");
                            
                // reads end of type element
                event = reader.nextEvent();
                message = parseTableStatus();
                            
            } // inner if
            else validXML = false;
                        
        } // outer if
        else
        {
            System.out.println("invalid message no type after body");
            return null;
        } // else           
        return message;
    }
    
} // XMLMessageParse
