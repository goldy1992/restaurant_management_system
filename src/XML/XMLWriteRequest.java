package XML;

import Client.MyClient;
import Message.Request.Request;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Goldy
 */
public class XMLWriteRequest 
{
    PrintWriter outStream;
    XMLOutputFactory factory;
    XMLEventFactory  eventFactory;
    XMLEventWriter writer;
    XMLEvent event;
    Request request;
    
    public XMLWriteRequest(PrintWriter outStream, Request request)
    {
        this.outStream = outStream;
        this.request = request;
    } // constructor
    

    
    public void tableStatusRequest(int[] tablesToCheck)
    {
        try
        {
            factory      = XMLOutputFactory.newInstance();
            eventFactory = XMLEventFactory.newInstance();
            writer = factory.createXMLEventWriter(this.outStream);
            
            StartDocument open = eventFactory.createStartDocument("UTF-8", "1.0");
            
            StartElement requestTag = eventFactory.createStartElement("", "", "request");
            
            StartElement to = eventFactory.createStartElement("", "", "to");
            Characters toBody = eventFactory.createCharacters(MyClient.serverAddress.getHostName());
            EndElement endTo = eventFactory.createEndElement("", "", "to");
            
            StartElement from = eventFactory.createStartElement("", "", "from");
            Characters fromBody = eventFactory.createCharacters(MyClient.client.getLocalAddress().getHostName());
            EndElement endFrom = eventFactory.createEndElement("", "", "from");
            
            StartElement ID = eventFactory.createStartElement("", "", "id");
            Characters IDBody = eventFactory.createCharacters(request.getMessageID());
            EndElement endID = eventFactory.createEndElement("", "", "id");
            
            StartElement bodyTag = eventFactory.createStartElement("", "", "body");
            StartElement type = eventFactory.createStartElement("", "", "type");
            Characters typeBody = eventFactory.createCharacters("TABLE_STATUS");
            EndElement endType = eventFactory.createEndElement("", "", "type");
            
            Attribute tableType = eventFactory.createAttribute("type",
                "Array");
            Attribute numOfTables = eventFactory.createAttribute("total",
                String.valueOf(tablesToCheck.length));
            List attributeList = Arrays.asList(tableType, numOfTables);
            List nsList = Arrays.asList();
            StartElement table = eventFactory.createStartElement("", "", "table", attributeList.iterator(), nsList.iterator());
            
            StartElement[] valueArray = new StartElement[tablesToCheck.length];
            Characters[] valueBodyArray = new Characters[tablesToCheck.length];
            EndElement[] valueEndArray = new EndElement[tablesToCheck.length];           
            for (int i = 0; i < tablesToCheck.length; i++)
            {
                valueArray[i] = eventFactory.createStartElement("", "", "value");
                valueBodyArray[i] = eventFactory.createCharacters(String.valueOf(tablesToCheck[i]));
                valueEndArray[i] = eventFactory.createEndElement("", "", "value");                
            } // for
            
            EndElement endTable = eventFactory.createEndElement("", "", "table");           
            EndElement endBodyTag = eventFactory.createEndElement("", "", "body");

            
            
            
            EndElement endRequest = eventFactory.createEndElement("", "", "request");
            
            writer.add(open);
            writer.add(requestTag);
            writer.add(to);
            writer.add(toBody);
            writer.add(endTo);
            writer.add(from);
            writer.add(fromBody);
            writer.add(endFrom);
            writer.add(ID);
            writer.add(IDBody);
            writer.add(endID);
            writer.add(bodyTag);
            writer.add(type);
            writer.add(typeBody);
            writer.add(endType);
            writer.add(table);
            for (int i = 0; i < tablesToCheck.length; i++)
            {
                writer.add(valueArray[i]);
                writer.add(valueBodyArray[i]);
                writer.add(valueEndArray[i]);                
            } // for
            writer.add(endTable);            
            writer.add(endBodyTag);
            writer.add(endRequest);
            writer.add(eventFactory.createEndDocument());
                                    

            writer.flush();
            writer.close();                
            System.out.println("Sent all info");
        }  // try
        catch (XMLStreamException e)
        {
            System.out.println(e);
        } // catch        
    } // sendRequest
} // class
