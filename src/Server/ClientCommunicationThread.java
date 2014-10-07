/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Message.EventNotification.EventNotification;
import Message.EventNotification.TableStatusEvtNfn;
import Message.Message;
import Message.Request.NumOfTablesRequest;
import Message.Request.Request;
import Message.Request.TableStatusRequest;
import Message.Response.NumOfTablesResponse;
import Message.Response.Response;
import Message.Response.TableStatusResponse;
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
    public long id;
    private ObjectOutputStream out;
    private ObjectInputStream in;   
    
    public ClientCommunicationThread(Socket socket, long id)
    {
        this.socket = socket;
        this.id = id;
        this.thread = new Thread(this);
    } // constructor
    
    @Override
    public void run()
    {

        System.out.println("got here");
        if (socket != null)
        {
            System.out.println(socket);
            try
            {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());    
                System.out.println("read message");
                               
                while(true)
                {
                    Message message = (Message) in.readObject();
                
                    if (message instanceof Request)
                    {
                        Response response = null;  
                        if (message instanceof TableStatusRequest)
                            response = new TableStatusResponse((TableStatusRequest)message);
                        else if (message instanceof NumOfTablesRequest)
                            response = new NumOfTablesResponse((NumOfTablesRequest)message);
                        System.out.println("reponse\n" + message);

                        response.parse();
                        out.writeObject(response);
                    } // the respose if statement                    
                    else if(message instanceof EventNotification)
                    {
                        if (message instanceof TableStatusEvtNfn)
                        {
                            TableStatusEvtNfn event = (TableStatusEvtNfn)message;
                            int tableNumber = event.getTableNumber();
                            Table.TableStatus status = event.getTableStatus();
                            MyServer.getTable(tableNumber).setTableStatus(status);
                                                        
                            for(int i =0; i < MyServer.getClients().size(); i++)
                            {
                                ObjectOutputStream otherClientOut = MyServer.getClients().get(i).getOutStream();
                                TableStatusEvtNfn msgToSend = new TableStatusEvtNfn(event.getToAddress(),
                                                                                    MyServer.getClients().get(i).getSocket().getInetAddress(),
                                                                                    event.getMessageID(),
                                                                                   tableNumber,
                                                                                    status);
                                
                                otherClientOut.writeObject(msgToSend);
                            }
                        } // tstatusnfnif
                        
                    }
                System.out.println("returned");
                }
                
            } // try            // try            // try            // try           
            catch(IOException e)
            {
                System.out.println("Failed to set up buffers" + "\n");
                e.printStackTrace();
            } 
            catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientCommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*catch (XMLStreamException e) 
            {
                e.printStackTrace();
            }*/
        }
        while (true);
    } // run
   
    public Socket getSocket()
    {
        return socket;
    }
    
    public Thread getThread()
    {
        return thread;
    }
    
    public ObjectOutputStream getOutStream()
    {
        return out;
    }
    
    public ObjectInputStream getInStream()
    {
        return in;
    }
}
