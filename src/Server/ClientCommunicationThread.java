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
            MyServer.debugGUI.addText("socket: " + socket);
            try
            {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());    

                               
                while(isRunning)
                {
                    Message message = (Message) in.readObject();
                         //MyServer.debugGUI.addText("read message");       
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
                e.printStackTrace();
                System.err.println(e);
                MyServer.debugGUI.addText("Failed to set up buffers" + "\n");
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
                    case REGISTER_BAR: MyServer.setBarClient(this); MyServer.debugGUI.addText("register bar to port: " + this.getSocket().getPort() ); break;
                    case REGISTER_KITCHEN: MyServer.setKitchenClient(this); MyServer.debugGUI.addText("register kitchen to port: " + this.getSocket().getPort() ); break;
                    case REGISTER_WAITER_CLIENT: MyServer.addWaiterClient(this); break;
                    default: break;
                }

            }
            response = rResponse;
            
            //if (response instanceof RegisterClientResponse)
                //MyServer.debugGUI.addText("response registered correctly");
        } // else if
        else if (message instanceof TabRequest)
        {
            response = new TabResponse((TabRequest)message);
            MyServer.debugGUI.addText("received Tab Request\n");
        }
        else if (message instanceof LeaveRequest)
        {
            response = new LeaveResponse((LeaveRequest)message);
            MyServer.removeWaiterClient(this);
            //MyServer.debugGUI.addText("client size " + MyServer.getClients().size());
            isRunning = false;
        }
        
        if (response == null)
            return;
        
        //MyServer.debugGUI.addText("reponse\n" + message);
        response.parse();
        out.reset();
        out.writeObject(response);        
    }
    
    private void parseEventNotification(EventNotification message) throws IOException
    {
        if (message instanceof TableStatusEvtNfn)
        {
            TableStatusEvtNfn event = (TableStatusEvtNfn)message;
            int tableNumber = event.getTableNumber();
            Table.TableStatus status = event.getTableStatus();
                           
            MyServer.debugGUI.addText("requested table " + tableNumber);
            MyServer.getTable(tableNumber).setTableStatus(status);
                            
            MyServer.debugGUI.addText("new table status " + MyServer.getTable(tableNumber).getTableStatus());
                                                        
            MyServer.debugGUI.addText("number of clients to send to: " + MyServer.getWaiterClients().size());

            for(int i =0; i < MyServer.getWaiterClients().size(); i++)
            {
                ObjectOutputStream otherClientOut = MyServer.getWaiterClients().get(i).getOutStream();
                TableStatusEvtNfn msgToSend = new TableStatusEvtNfn(event.getToAddress(),
                        MyServer.getWaiterClients().get(i).getSocket().getInetAddress(),
                        event.getMessageID(),
                        tableNumber,
                        status);
                                
                otherClientOut.reset();
                otherClientOut.writeObject(msgToSend);
                MyServer.debugGUI.addText("sent to port " + MyServer.getWaiterClients().get(i).getSocket().getPort() + "   " + i);
            } // for
        } // if
        else if (message instanceof TabUpdateNfn)
        {
            TabUpdateNfn event = (TabUpdateNfn)message;
            int tableNumber = event.getTab().getTable().getTableNumber();
            MyServer.getTable(tableNumber).updateTab(event.getTab());
            //MyServer.debugGUI.addText("Tab updated");              
        } // else if
        else if (message instanceof NewItemNfn)
        {
            MyServer.debugGUI.addText("bar port: " + MyServer.getBarClient().getSocket().getPort());
            MyServer.debugGUI.addText("kitchen port: " + MyServer.getKitchenClient().getSocket().getPort());
            NewItemNfn event = (NewItemNfn)message;
            MyServer.debugGUI.addText("new notifaction for " + event.getType());
            if (event.getType() == Item.Type.DRINK && MyServer.getBarClient() != null)
            {
                MyServer.debugGUI.addText("got here drink: send to port: " + MyServer.getBarClient().getSocket().getPort());
                //ObjectOutputStream otherClientOut = MyServer.getBarClient().getOutStream();
                                MyServer.debugGUI.addText("got here drink: send to port: " + MyServer.getBarClient().getSocket().getPort());
                NewItemNfn msgToSend = new NewItemNfn(event.getToAddress(),
                        MyServer.getBarClient().getSocket().getInetAddress(),
                        event.getMessageID(),
                        event.getType(),
                        event.getItems(),
                        event.getTable());
                MyServer.getBarClient().getOutStream().reset();
                MyServer.getBarClient().getOutStream().writeObject(msgToSend);
                MyServer.debugGUI.addText("sent to port: " + MyServer.getBarClient().getSocket().getPort());
            } // if
            
            else if (event.getType() == Item.Type.FOOD && MyServer.getKitchenClient() != null)
            { 
                MyServer.debugGUI.addText("got here food: send to port: " + MyServer.getBarClient().getSocket().getPort());
                ObjectOutputStream otherClientOut = MyServer.getKitchenClient().getOutStream();
                NewItemNfn msgToSend = new NewItemNfn(event.getToAddress(),
                        MyServer.getKitchenClient().getSocket().getInetAddress(),
                        event.getMessageID(),
                        event.getType(),
                        event.getItems(),
                        event.getTable());
                otherClientOut.reset();
                otherClientOut.writeObject(msgToSend);
                MyServer.debugGUI.addText("sent to port: " + MyServer.getKitchenClient().getSocket().getPort());
            } // if
        } // else if
    }

} // class
