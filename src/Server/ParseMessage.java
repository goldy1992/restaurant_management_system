package Server;

import Item.Item;
import Message.EventNotification.EventNotification;
import Message.EventNotification.NewItemNfn;
import Message.EventNotification.TabUpdateNfn;
import Message.EventNotification.TableStatusEvtNfn;
import Message.Message;
import Message.Request.LeaveRequest;
import Message.Request.NumOfTablesRequest;
import Message.Request.RegisterClientRequest;
import Message.Request.Request;
import Message.Request.TabRequest;
import Message.Request.TableStatusRequest;
import static Message.Request.TableStatusRequest.ALL;
import Message.Response.LeaveResponse;
import Message.Response.NumOfTablesResponse;
import Message.Response.RegisterClientResponse;
import Message.Response.Response;
import Message.Response.TabResponse;
import Message.Response.TableStatusResponse;
import Message.Table;
import Message.TableList;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Terry
 */
public class ParseMessage 
{
    private final Message message;
    private final ClientConnection client;
    private final MyServer server;
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
        else if(message instanceof EventNotification)
            parseEventNotification((EventNotification)message);        
    } // parse
    
    private TableStatusResponse parseTableStatusRequest(Request request)
    {
        TableStatusResponse response = 
            new TableStatusResponse((TableStatusRequest)request);        
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
    
    private NumOfTablesResponse parseNumOfTablesRequest(Request request)
    {
        NumOfTablesResponse response = new NumOfTablesResponse((NumOfTablesRequest)request);   
        return response;
    }
    
    private RegisterClientResponse parseRegisterClientRequest(Request request)
    {
        RegisterClientResponse response = new RegisterClientResponse((RegisterClientRequest)request);
        boolean permission;
        
        switch(response.getClientType())
        {
            case BAR:
                permission = server.getBarClient() == null;
                response.setPermission(permission); 
                System.out.println("dealing with bar req"); 
                break;
            case KITCHEN:
                permission = server.getKitchenClient() == null;
                response.setPermission(permission); 
                System.out.println("dealing with kitchen req"); 
                break;
            case WAITER: response.setPermission(true);
            case TILL: response.setPermission(true);
            default: break;
        } // switch      
       
        if (response.hasPermission())
        {
            switch (response.getClientType())
            {
                case BAR: server.setBarClient(client); break;
                case KITCHEN: server.setKitchenClient(client);  break;
                case WAITER: server.getWaiterClients().add(client);  break;
                case TILL: server.getTillClients().add(client);  break;
                default: break;
            } // switch
        }
        return response;
    }
    
    private TabResponse parseTabRequest(Request request)
    {
        TabResponse response = new TabResponse((TabRequest)request);
        TabRequest tabRequest = (TabRequest)response.getRequest();
        int number  = tabRequest.getTabNumber();
        TableList tables = client.getServer().getTables();
        response.setTab(tables.getTable(number).getCurrentTab());
        return response;
    }
    
    private LeaveResponse parseLeaveRequest(Request request)
    {
        LeaveResponse response = new LeaveResponse((LeaveRequest)request); 
        server.getWaiterClients().remove(client);
        response.setPermission(true);
        client.isRunning = false;
        return response;
    }
    
    private boolean parseRequest(Request request) throws IOException
    {
        Response response = null;  
        if (request instanceof TableStatusRequest)
            response = parseTableStatusRequest(request);
        else if (request instanceof NumOfTablesRequest)
            response = parseNumOfTablesRequest(request);
        else if (request instanceof RegisterClientRequest)  
            response = parseRegisterClientRequest(request);
        else if (request instanceof TabRequest)
            response = parseTabRequest(request);
        else if (request instanceof LeaveRequest)
        response = parseLeaveRequest(request);
                       
        response.setParsed(true);    
        return sendMessage(response, client.getOutStream());
    }
    
    private void parseTableStatusEvtNfn(EventNotification message) 
    {
        TableStatusEvtNfn event = (TableStatusEvtNfn)message;
        int tableNumber = event.getTableNumber();
        Table.TableStatus status = event.getTableStatus();
                          
        //gui.addText("requested table " + tableNumber);
        tables.getTable(tableNumber).setTableStatus(status);
                            
        //gui.addText("new table status " + parent.getTable(tableNumber).getTableStatus());
                                                       
        //gui.addText("number of clients to send to: " + parent.getWaiterClients().size());

        for (ClientConnection c : server.getWaiterClients())
        {
            ObjectOutputStream otherClientOut = c.getOutStream();
            TableStatusEvtNfn msgToSend = new TableStatusEvtNfn(event.getToAddress(),
                    c.getSocket().getInetAddress(),
                    event.getMessageID(),
                    tableNumber,
                    status);
            sendMessage(msgToSend, otherClientOut);
        }  
        for (ClientConnection c : server.getTillClients())
        {
            ObjectOutputStream otherClientOut = c.getOutStream();
            TableStatusEvtNfn msgToSend = new TableStatusEvtNfn(event.getToAddress(),
                    c.getSocket().getInetAddress(),
                    event.getMessageID(),
                    tableNumber,
                    status);
            sendMessage(msgToSend, otherClientOut);
        } 
        
    } // parseTableStatusEvtNfn
    
    private void parseTabUpdateNfn(EventNotification message)
    {
        TabUpdateNfn event = (TabUpdateNfn)message;
        int tableNumber = event.getTab().getTable().getTableNumber();
        
        if (tableNumber >= 1 && tableNumber <= tableNumber)
        {
            tables.getTable(tableNumber).updateTab(event.getTab());
        }
        //gui.addText("Tab updated");          
    } // parseTabUpdateNfn
    
    private void parseNewItemNfn(EventNotification message) 
    {
        NewItemNfn event = (NewItemNfn)message;

        if (event.getType() == Item.Type.DRINK && server.getBarClient() != null)
        {
            ObjectOutputStream otherClientOut = server.getBarClient().getOutStream();

            NewItemNfn msgToSend1 = new NewItemNfn(event.getToAddress(),
                    server.getBarClient().getSocket().getInetAddress(),
                    event.getMessageID(),
                    event.getType(),
                    event.getItems(),
                    event.getTable());

            sendMessage(msgToSend1, otherClientOut);
        } // if

        else if (event.getType() == Item.Type.FOOD && server.getKitchenClient() != null)
        { 
            //gui.addText("got here food: send to port: " + parent.getBarClient().getSocket().getPort());
            ObjectOutputStream otherClientOut = server.getKitchenClient().getOutStream();
            NewItemNfn msgToSend = new NewItemNfn(event.getToAddress(),
                   server.getKitchenClient().getSocket().getInetAddress(),
                    event.getMessageID(),
                    event.getType(),
                    event.getItems(),
                    event.getTable());
            sendMessage(msgToSend, otherClientOut);
        } // if      
   }
    
    private void parseEventNotification(EventNotification message) 
    {
        if (message instanceof TableStatusEvtNfn)
            parseTableStatusEvtNfn(message);
        else if (message instanceof TabUpdateNfn)
            parseTabUpdateNfn(message);
        else if (message instanceof NewItemNfn)
            parseNewItemNfn(message);
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
