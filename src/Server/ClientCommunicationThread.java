/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Message.Table;
import Item.*;
import Message.*;
import Message.Request.*;
import Message.EventNotification.*;
import Message.Response.*;
import Client.OutputGUI;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Goldy
 */
public class ClientCommunicationThread implements Runnable
{
    private final MyServer parent;
    private final Socket socket;
    private final Thread thread;
    public long id;
    private ObjectOutputStream out;
    private ObjectInputStream in;   
    private boolean isRunning;
    public OutputGUI gui;
    
    /**
     *
     * @param socket
     * @param id
     * @throws java.net.SocketException
     */
    public ClientCommunicationThread(Socket socket, long id, MyServer parent) throws SocketException
    {
        this.socket = socket;
        this.id = id;
        this.parent = parent;
        this.isRunning = true;
        this.thread = new Thread(this);
        //gui = new OutputGUI();
        System.out.println("buffer size: " + socket.getReceiveBufferSize());
        //gui.setVisible(true);
    } // constructor
    
    @Override
    public void run()
    {
        if (socket != null)
        {
            Message message = null;
            try
            {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());    
                              
                while(isRunning)
                {
                    message = (Message) in.readObject();
                          
                    if (message instanceof Request)
                        parseRequest((Request)message);                  
                    else if(message instanceof EventNotification)
                        parseEventNotification((EventNotification)message);
                } // while
                
            } // try        

            catch (EOFException e)
            {
               System.err.println(e.getCause() + "\n " + e.getLocalizedMessage() +  "\n  " + e.getMessage());
                Logger.getLogger(ClientCommunicationThread.class.getName()).log(Level.SEVERE, null, e);
                
            }
             catch(IOException e)
            {
            } // catch
            catch (ClassNotFoundException  ex) 
            {
                Logger.getLogger(ClientCommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
            } // catch            
        } // if socket == null
    } // run
   
    /**
     *
     * @return
     */
    public Socket getSocket()
    {
        return socket;
    }
    
    /**
     *
     * @return
     */
    public Thread getThread()
    {
        return thread;
    }
    
    /**
     *
     * @return
     */
    public ObjectOutputStream getOutStream()
    {
        return out;
    }
    
    /**
     *
     * @return
     */
    public ObjectInputStream getInStream()
    {
        return in;
    }
    
    private TableStatusResponse parseTableStatusRequest(Request request)
    {
        TableStatusResponse response = 
            new TableStatusResponse((TableStatusRequest)request);        
        // cast the request to a table request
        TableStatusRequest x = (TableStatusRequest)response.getRequest();
 
        if (x.getTableList().size() == 1 && x.getTableList().get(0) == -1)
        {
            response.getTableStatuses()
                    .add(null);
            for (int i = 1; i <= parent.getNumOfTables(); i++)
                response.getTableStatuses()
                    .add(parent.getTable(i).getTableStatus());            
        }
        else
        {
            for (int i = 0; i < x.getTableList().size(); i++)
            {
                response.getTableStatuses()
                    .add(parent
                            .getTable(x.getTableList().get(i))
                                .getTableStatus());
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
      
        switch(response.getClientType())
        {
            case BAR: response.setPermission(parent.getBarClient() == null); System.out.println("dealing with bar req"); break;
            case KITCHEN: response.setPermission(parent.getKitchenClient() == null); System.out.println("dealing with kitchen req"); break;
            case WAITER: response.setPermission(true);
            case TILL: response.setPermission(true);
            default: break;
        } // switch      
       
        if (response.hasPermission())
        {
            switch (response.getClientType())
            {
                case BAR: parent.setBarClient(this); break;
                case KITCHEN: parent.setKitchenClient(this);  break;
                case WAITER: parent.getWaiterClients().add(this);  break;
                case TILL: parent.getTillClients().add(this);  break;
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
        response.setTab(parent.getTable(number).getCurrentTab());
        return response;
    }
    
    private LeaveResponse parseLeaveRequest(Request request)
    {
        LeaveResponse response = new LeaveResponse((LeaveRequest)request); 
        parent.getWaiterClients().remove(this);
        response.setPermission(true);
        isRunning = false;
        return response;
    }
    
    private void parseRequest(Request request) throws IOException
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
               
        if (response == null)
            return;
        
        response.setParsed(true);   
        out.reset();
        out.writeObject(response);        
    }
    
    private void parseTableStatusEvtNfn(EventNotification message) throws IOException
    {
        TableStatusEvtNfn event = (TableStatusEvtNfn)message;
        int tableNumber = event.getTableNumber();
        Table.TableStatus status = event.getTableStatus();
                          
        //gui.addText("requested table " + tableNumber);
        parent.getTable(tableNumber).setTableStatus(status);
                            
        //gui.addText("new table status " + parent.getTable(tableNumber).getTableStatus());
                                                       
        //gui.addText("number of clients to send to: " + parent.getWaiterClients().size());

        for (ClientCommunicationThread c : parent.getWaiterClients())
        {
            ObjectOutputStream otherClientOut = c.getOutStream();
            TableStatusEvtNfn msgToSend = new TableStatusEvtNfn(event.getToAddress(),
                    c.getSocket().getInetAddress(),
                    event.getMessageID(),
                    tableNumber,
                    status);
                        otherClientOut.reset();
            otherClientOut.writeObject(msgToSend);
        }  
        for (ClientCommunicationThread c : parent.getTillClients())
        {
            ObjectOutputStream otherClientOut = c.getOutStream();
            TableStatusEvtNfn msgToSend = new TableStatusEvtNfn(event.getToAddress(),
                    c.getSocket().getInetAddress(),
                    event.getMessageID(),
                    tableNumber,
                    status);
                        otherClientOut.reset();
            otherClientOut.writeObject(msgToSend);
        } 
        
    } // parseTableStatusEvtNfn
    
    private void parseTabUpdateNfn(EventNotification message)
    {
        TabUpdateNfn event = (TabUpdateNfn)message;
        int tableNumber = event.getTab().getTable().getTableNumber();
        
        if (tableNumber >= 1 && tableNumber <= parent.getNumOfTables())
        parent.getTable(tableNumber).updateTab(event.getTab());
        //gui.addText("Tab updated");          
    }
    
    private void parseNewItemNfn(EventNotification message) throws IOException
    {
            //gui.addText("id: " + id + ", bar port: " + parent.getBarClient().getSocket().getPort());
            //gui.addText("id: " + id + ", kitchen port: " + parent.getKitchenClient().getSocket().getPort());
            NewItemNfn event = (NewItemNfn)message;
            //gui.addText("id: " + id + ", new notifaction for " + event.getType());
            
            if (event.getType() == Item.Type.DRINK && parent.getBarClient() != null)
            {
                ObjectOutputStream otherClientOut = parent.getBarClient().getOutStream();
           
                NewItemNfn msgToSend1 = new NewItemNfn(event.getToAddress(),
                        parent.getBarClient().getSocket().getInetAddress(),
                        event.getMessageID(),
                        event.getType(),
                        event.getItems(),
                        event.getTable());
                otherClientOut.reset();
                                     //gui.addText("id: " + id + ", got here drink: send to port: " + parent.getBarClient().getOutStream().toString());
                otherClientOut.writeObject(msgToSend1);
                //gui.addText("id: " + id + ", sent to port: " + parent.getBarClient().getSocket().getPort());
                
            } // if
            
            else if (event.getType() == Item.Type.FOOD && parent.getKitchenClient() != null)
            { 
                //gui.addText("got here food: send to port: " + parent.getBarClient().getSocket().getPort());
                ObjectOutputStream otherClientOut = parent.getKitchenClient().getOutStream();
                NewItemNfn msgToSend = new NewItemNfn(event.getToAddress(),
                       parent.getKitchenClient().getSocket().getInetAddress(),
                        event.getMessageID(),
                        event.getType(),
                        event.getItems(),
                        event.getTable());
                otherClientOut.reset();
         //gui.addText("id: " + id + ", port difference: " + parent.getKitchenClient().getSocket().getPort() + " " + parent.getBarClient().getSocket().getPort());  
         //gui.addText("id: " + id + ", Out Stream difference: " + parent.getKitchenClient().getOutStream() + " " + parent.getBarClient().getOutStream());
                otherClientOut.writeObject(msgToSend);
               // gui.addText("id: " + id + ", sent to port: " + parent.getKitchenClient().getSocket().getPort());
            } // if      
   }
    
    private void parseEventNotification(EventNotification message) throws IOException
    {
        if (message instanceof TableStatusEvtNfn)
            parseTableStatusEvtNfn(message);
        else if (message instanceof TabUpdateNfn)
            parseTabUpdateNfn(message);
        else if (message instanceof NewItemNfn)
            parseNewItemNfn(message);
    }

} // class
