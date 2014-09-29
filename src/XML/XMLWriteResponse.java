/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XML;

import Client.MyClient;
import XML.Message.Request.TableStatusRequest;
import XML.Message.Response.Response;
import XML.Message.Response.TableStatusResponse;
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

/**
 *
 * @author Goldy
 */
public class XMLWriteResponse 
{
    PrintWriter outStream;
    XMLOutputFactory factory;
    XMLEventFactory  eventFactory;
    XMLEventWriter writer;
    XMLEvent event;
    Response response;
    
    

    public XMLWriteResponse(PrintWriter outStream, Response response)
    {
        this.outStream = outStream;
        this.response = response;
        
    } // constructor
      
    public void writeXMLHeader() throws XMLStreamException
    {
        StartDocument open = eventFactory.createStartDocument("UTF-8", "1.0");        
        StartElement request = eventFactory.createStartElement("", "", "response");
            
        StartElement to = eventFactory.createStartElement("", "", "to");
        // FILL IN 
        Characters toBody = eventFactory.createCharacters(response.getToAddress().getHostName());
        EndElement endTo = eventFactory.createEndElement("", "", "to");
           
        StartElement from = eventFactory.createStartElement("", "", "from");
        Characters fromBody = eventFactory.createCharacters(response.getFromAddress().getHostName());
        EndElement endFrom = eventFactory.createEndElement("", "", "from");
            
        StartElement ID = eventFactory.createStartElement("", "", "id");
        Characters IDBody = eventFactory.createCharacters(response.getMessageID());
        EndElement endID = eventFactory.createEndElement("", "", "id");
        
         writer.add(open);
         writer.add(request);
         writer.add(to);
         writer.add(toBody);
         writer.add(endTo);
         writer.add(from);
         writer.add(fromBody);
         writer.add(endFrom);
         writer.add(ID);
         writer.add(IDBody);
         writer.add(endID);
    } // write xml header
      
    public void tableStatusRequest()
    {
        if (!(response instanceof TableStatusResponse))
            return;
        
        TableStatusResponse thisResponse = (TableStatusResponse)response;
        int arraySize = thisResponse.getTableStatuses().size();
        System.out.println("array size: " + arraySize);
        try
        {
            factory      = XMLOutputFactory.newInstance();
            eventFactory = XMLEventFactory.newInstance();
            writer = factory.createXMLEventWriter(this.outStream);
            
            writeXMLHeader();
            
            StartElement bodyTag = eventFactory.createStartElement("", "", "body");
            StartElement type = eventFactory.createStartElement("", "", "type");
            Characters typeBody = eventFactory.createCharacters("TABLE_STATUS");
            EndElement endType = eventFactory.createEndElement("", "", "type");
            
            Attribute tableType = eventFactory.createAttribute("type",
                "Array");
            Attribute numOfTables = eventFactory.createAttribute("total",
                String.valueOf(arraySize) );
            List attributeList = Arrays.asList(tableType, numOfTables);
            List nsList = Arrays.asList();
            StartElement table = eventFactory.createStartElement("", "", "table", attributeList.iterator(), nsList.iterator());
            
            StartElement[] valueArray = new StartElement[arraySize];
            Characters[] valueBodyArray = new Characters[arraySize];
            EndElement[] valueEndArray = new EndElement[arraySize];     
            
            for (int i = 0; i < arraySize; i++)
            {
                valueArray[i] = eventFactory.createStartElement("", "", "value");
                switch(thisResponse.getTableStatuses().get(i))
                {
                    case FREE:
                        valueBodyArray[i] = eventFactory.createCharacters("FREE");
                        System.out.println( i + ": FREE");
                        break;
                    case OCCUPIED:
                        valueBodyArray[i] = eventFactory.createCharacters("OCCUPIED");
                        System.out.println( i + ": OCCUPIED");                        
                        break;
                    case IN_USE:
                        valueBodyArray[i] = eventFactory.createCharacters("IN_USE");
                                                System.out.println( i + ": IN_USE");
                        break;                        
                } // switch
                valueEndArray[i] = eventFactory.createEndElement("", "", "value");                
            } // for
            
            EndElement endTable = eventFactory.createEndElement("", "", "table");           
            EndElement endBodyTag = eventFactory.createEndElement("", "", "body");           
            EndElement endRequest = eventFactory.createEndElement("", "", "response");
            
           
            writer.add(bodyTag);
            writer.add(type);
            writer.add(typeBody);
            writer.add(endType);
            writer.add(table);
            for (int i = 0; i < arraySize; i++)
            {
                 System.out.println("going to write: " + i);
                writer.add(valueArray[i]);
                writer.add(valueBodyArray[i]);
                writer.add(valueEndArray[i]);  
                System.out.println("witten: " + i);
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

    

