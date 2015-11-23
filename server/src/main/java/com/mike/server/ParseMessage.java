package com.mike.server;

import com.mike.item.Item;
import com.mike.message.*;
import com.mike.message.Request.*;
import com.mike.message.Response.*;
import com.mike.message.EventNotification.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import static com.mike.message.Request.TableStatusRequest.ALL;

/**
 *
 * @author Terry
 */
public class ParseMessage {
    private final Message message;
    private final ClientConnection client;
    private final Server server;
    private final TableList tables;
    
    public ParseMessage(Message m, ClientConnection client)
    {
        this.message = m;
        this.client = client;
        this.server = client.getServer();
        this.tables = server.getTables();
    } // parseMessage
    
    public void parse() throws IOException
    {
        if (message instanceof Request)
            parseRequest((Request)message);                  
     //   else if(message instanceof EventNotification)
       //     parseEventNotification((EventNotification)message);        
    } // parse
    
    private TableStatusResponse parseTableStatusRequest(TableStatusRequest request)
    {
        TableStatusResponse response = 
            new TableStatusResponse(request);        
        // cast the request to a table request
        TableStatusRequest x = (TableStatusRequest)response.getRequest();
        
        int numOfTables = tables.getSize();
        
        if (x.getFirstValue() == ALL)
        {
            response.getStatuses().add(null);
            for (int i = 1; i <= numOfTables; i++)
            {
                Table table = tables.getTable(i);
                response.getStatuses().add(table.getStatus());
            } // for
        }
        else
        {
            for (int i = 1; i < x.getTableList().size(); i++)
            {
                Table table = tables.getTable(i);
                response.getStatuses().add(table.getStatus());
            } // for
        }

        return response;
    }
    
    private NumOfTablesResponse parseNumOfTablesRequest(NumOfTablesRequest request)
    {
        NumOfTablesResponse response = new NumOfTablesResponse(request);   
        return response;
    }
    
    
    @ServiceActivator(inputChannel="messageRegisterClientRequestChannel")
    private RegisterClientResponse parseRegisterClientRequest(RegisterClientRequest request)
    {
    	System.out.println("hit register client");
        RegisterClientResponse response = new RegisterClientResponse(request);
        boolean hasPermission;
        
        switch(response.getClientType())
        {
            case BAR:
                hasPermission = server.getBarClient() == null;
                response.setPermission(hasPermission); 
                System.out.println("dealing with bar req"); 
                if (hasPermission)
                {
                    server.setBarClient(client);    
                }
                break;
            case KITCHEN:
                hasPermission = server.getKitchenClient() == null;
                response.setPermission(hasPermission); 
                System.out.println("dealing with kitchen req");
                if (hasPermission)
                {
                    server.setKitchenClient(client);    
                }
                break;
            case WAITER: 
                response.setPermission(true);
                break;
            case TILL: 
                response.setPermission(true); 
                break;
            default: break;
        } // switch      
       
        return response;
    }
    
    private TabResponse parseTabRequest(TabRequest request)
    {
        TabResponse response = new TabResponse(request);
        int number  = request.getTabNumber();
        TableList tables = server.getTables();
        response.setTab(tables.getTable(number).getCurrentTab());
        return response;
    }
    
    private LeaveResponse parseLeaveRequest(LeaveRequest request)
    {
        LeaveResponse response = new LeaveResponse((LeaveRequest)request); 
        server.getWaiterClients().remove(client);
        response.setPermission(true);
        client.stop();
        return response;
    }
    
    private boolean parseRequest(Request request) throws IOException
    {
        Response response;
        
        if (request instanceof TableStatusRequest)
        {
            response = parseTableStatusRequest((TableStatusRequest)request);
        }
        else if (request instanceof NumOfTablesRequest)
        {
            response = parseNumOfTablesRequest((NumOfTablesRequest)request);
        }
        else if (request instanceof RegisterClientRequest)  
        {
            response = parseRegisterClientRequest((RegisterClientRequest)request);
        }
        else if (request instanceof TabRequest)
        {
            response = parseTabRequest((TabRequest)request);
        }
        else if (request instanceof LeaveRequest)
        {
        response = parseLeaveRequest((LeaveRequest)request);
        }
        else 
        {
            return false;
        }
        
        response.setParsed(true);    
        return sendMessage(response, client.getOutStream());
    }
    
//    private void parseTableStatusEvtNfn(EventNotification message) 
//    {
//        TableStatusEvtNfn event = (TableStatusEvtNfn)message;
//        int tableNumber = event.getTableNumber();
//        Table.TableStatus status = event.getTableStatus();
//                          
//        //gui.addText("requested table " + tableNumber);
//        tables.getTable(tableNumber).setTableStatus(status);
//                            
//        //gui.addText("new table status " + parent.getTable(tableNumber).getTableStatus());
//                                                       
//        //gui.addText("number of clients to send to: " + parent.getWaiterClients().size());
//
//        for (ClientConnection c : server.getWaiterClients())
//        {
//            ObjectOutputStream otherClientOut = c.getOutStream();
//            TableStatusEvtNfn msgToSend = new TableStatusEvtNfn(event.getToAddress(),
//                    c.getSocket().getInetAddress(),
//                    tableNumber,
//                    status);
//            sendMessage(msgToSend, otherClientOut);
//        }  
//        for (ClientConnection c : server.getTillClients())
//        {
//            ObjectOutputStream otherClientOut = c.getOutStream();
//            TableStatusEvtNfn msgToSend = new TableStatusEvtNfn(event.getToAddress(),
//                    c.getSocket().getInetAddress(),
//                    tableNumber,
//                    status);
//            sendMessage(msgToSend, otherClientOut);
//        } 
//        
//    } // parseTableStatusEvtNfn
    
    private void parseTabUpdateNfn(EventNotification message)
    {
        TabUpdateNfn event = (TabUpdateNfn)message;
        Integer tableNumber = event.getTab().getTable();
        
        if (tableNumber >= 1 && tableNumber <= tableNumber)
        {
            tables.getTable(tableNumber).updateTab(event.getTab());
        }
        //gui.addText("Tab updated");          
    } // parseTabUpdateNfn
    
//   private void parseNewItemNfn(EventNotification message) 
//    {
//        NewItemNfn event = (NewItemNfn)message;
//
//        if (event.getType() == Item.Type.DRINK && server.getBarClient() != null)
//        {
//            ObjectOutputStream otherClientOut = server.getBarClient().getOutStream();
//
//            NewItemNfn msgToSend1 = new NewItemNfn(event.getToAddress(),
//                    server.getBarClient().getSocket().getInetAddress(),
//                    event.getType(),
//                    event.getItems(),
//                    event.getTable());
//
//            sendMessage(msgToSend1, otherClientOut);
//        } // if
//
//        else if (event.getType() == Item.Type.FOOD && server.getKitchenClient() != null)
//        { 
//            //gui.addText("got here food: send to port: " + parent.getBarClient().getSocket().getPort());
//            ObjectOutputStream otherClientOut = server.getKitchenClient().getOutStream();
//            NewItemNfn msgToSend = new NewItemNfn(event.getToAddress(),
//                   server.getKitchenClient().getSocket().getInetAddress(),
//                    event.getType(),
//                    event.getItems(),
//                    event.getTable());
//            sendMessage(msgToSend, otherClientOut);
//        } // if      
//   }
    
    private void parseEventNotification(EventNotification message) 
    {
        if (message instanceof TableStatusEvtNfn);
           // parseTableStatusEvtNfn(message);
        else if (message instanceof TabUpdateNfn)
            parseTabUpdateNfn(message);
        else if (message instanceof NewItemNfn);
        //    parseNewItemNfn(message);
    } // parseEventNotification

    private <T extends Message> boolean sendMessage(T message, 
                                                    ObjectOutputStream out)
    {
        try 
        {
            out.reset();
            out.writeObject(message);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ParseMessage.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
