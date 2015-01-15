/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Item.*;
import Message.*;
import Message.Request.*;
import Message.EventNotification.*;
import static Message.Request.Request.RequestType.REGISTER_BAR;
import Message.Response.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Goldy
 */
public class ClientCommunicationThread implements Runnable
{
    private final Socket socket;
    private final Thread thread;

    /**
     *
     */
    public long id;
    private ObjectOutputStream out;
    private ObjectInputStream in;   
    private boolean isRunning;
    
    /**
     *
     * @param socket
     * @param id
     */
    public ClientCommunicationThread(Socket socket, long id)
    {
        this.socket = socket;
        this.id = id;
        this.isRunning = true;
        this.thread = new Thread(this);
    } // constructor
    
    @Override
    public void run()
    {
        if (socket != null)
        {
            System.out.println(socket);
            try
            {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());    

                               
                while(isRunning)
                {
                    Message message = (Message) in.readObject();
                         //System.out.println("read message");       
                    if (message instanceof Request)
                    {
                        parseRequest((Request)message);                  
                    }
                    else if(message instanceof EventNotification)
                    {
                        parseEventNotification((EventNotification)message);
                    }
                    
                } // while
                
            } // try            // try            // try            // try           
            catch(IOException e)
            {
                System.err.println(e);
                System.out.println("Failed to set up buffers" + "\n");
            } 
            catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientCommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    private void parseRequest(Request message) throws IOException
    {
        Response response = null;  
        if (message instanceof TableStatusRequest)
            response = new TableStatusResponse((TableStatusRequest)message);
        else if (message instanceof NumOfTablesRequest)
            response = new NumOfTablesResponse((NumOfTablesRequest)message);
        else if (message instanceof RegisterClientRequest)
        {
            response = new RegisterClientResponse((RegisterClientRequest)message);

            RegisterClientResponse rResponse = (RegisterClientResponse)response;
            rResponse.parse();
                
            // register the bar/kitchen
            if (rResponse.hasPermission())
            {
                switch (message.type)
                {
                    case REGISTER_BAR: MyServer.setBarClient(this); break;
                    case REGISTER_KITCHEN: MyServer.setKitchenClient(this); break;
                    case REGISTER_WAITER_CLIENT: MyServer.addWaiterClient(this); break;
                    default: break;
                }

            }
            response = rResponse;
            
            //if (response instanceof RegisterClientResponse)
                //System.out.println("response registered correctly");
        } // else if
        else if (message instanceof TabRequest)
        {
            response = new TabResponse((TabRequest)message);
            System.out.println("received Tab Request\n");
        }
        else if (message instanceof LeaveRequest)
        {
            response = new LeaveResponse((LeaveRequest)message);
            MyServer.removeWaiterClient(this);
            //System.out.println("client size " + MyServer.getClients().size());
            isRunning = false;
        }
        
        if (response == null)
            return;
        
        //System.out.println("reponse\n" + message);
        response.parse();
        out.writeObject(response);        
    }
    
    private void parseEventNotification(EventNotification message) throws IOException
    {
        if (message instanceof TableStatusEvtNfn)
        {
            TableStatusEvtNfn event = (TableStatusEvtNfn)message;
            int tableNumber = event.getTableNumber();
            Table.TableStatus status = event.getTableStatus();
                           
            System.out.println("requested table " + tableNumber);
            MyServer.getTable(tableNumber).setTableStatus(status);
                            
            System.out.println("new table status " + MyServer.getTable(tableNumber).getTableStatus());
                                                        
            System.out.println("number of clients to send to: " + MyServer.getWaiterClients().size());

            for(int i =0; i < MyServer.getWaiterClients().size(); i++)
            {
                ObjectOutputStream otherClientOut = MyServer.getWaiterClients().get(i).getOutStream();
                TableStatusEvtNfn msgToSend = new TableStatusEvtNfn(event.getToAddress(),
                        MyServer.getWaiterClients().get(i).getSocket().getInetAddress(),
                        event.getMessageID(),
                        tableNumber,
                        status);
                                
                otherClientOut.writeObject(msgToSend);
                System.out.println("sent to port " + MyServer.getWaiterClients().get(i).getSocket().getPort() + "   " + i);
            } // for
        } // if
        else if (message instanceof TabUpdateNfn)
        {
            TabUpdateNfn event = (TabUpdateNfn)message;
            int tableNumber = event.getTab().getTable().getTableNumber();
            MyServer.getTable(tableNumber).updateTab(event.getTab());
            //System.out.println("Tab updated");              
        } // else if
        else if (message instanceof NewItemNfn)
        {
            NewItemNfn event = (NewItemNfn)message;
            System.out.println("new notifaction for " + event.getType());
            if (event.getType() == Item.Type.DRINK && MyServer.getBarClient() != null)
            {
                System.out.println("got here drink");
                ObjectOutputStream otherClientOut = MyServer.getBarClient().getOutStream();
                NewItemNfn msgToSend = new NewItemNfn(event.getToAddress(),
                        MyServer.getBarClient().getSocket().getInetAddress(),
                        event.getMessageID(),
                        event.getType(),
                        event.getItems(),
                        event.getTable());
                otherClientOut.writeObject(msgToSend);
                System.out.println("sent to port: " + MyServer.getBarClient().getSocket().getPort());
            } // if
            
            else if (event.getType() == Item.Type.FOOD && MyServer.getKitchenClient() != null)
            { 
                                System.out.println("got here food");
                ObjectOutputStream otherClientOut = MyServer.getKitchenClient().getOutStream();
                NewItemNfn msgToSend = new NewItemNfn(event.getToAddress(),
                        MyServer.getKitchenClient().getSocket().getInetAddress(),
                        event.getMessageID(),
                        event.getType(),
                        event.getItems(),
                        event.getTable());
                otherClientOut.writeObject(msgToSend);
                System.out.println("sent to port: " + MyServer.getKitchenClient().getSocket().getPort());
            } // if
        } // else if
    }

} // class
