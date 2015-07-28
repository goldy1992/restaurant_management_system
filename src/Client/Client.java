package Client;

import Message.EventNotification.EventNotification;
import Message.Message;
import Message.Request.LeaveRequest;
import Message.Request.RegisterClientRequest;
import Message.Request.RegisterClientRequest.ClientType;
import Message.Response.LeaveResponse;
import Message.Response.RegisterClientResponse;
import Message.Response.Response;
import Server.Server;
import Server.StartServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbbx9mg3
 */
public abstract class Client implements Runnable
{
    // localhost
    public final InetAddress serverAddress; 
    public final InetAddress address;
    public int serverPort;  
    public Socket client;
    public OutputGUI debugGUI;
    public Thread responseThread;    
    protected ObjectOutputStream out = null;
    private final ClientType type;
    private static final long serialVersionUID = 1L;
    
    // object classes
    public boolean running = true;    
    @Override
    public void run()
    {
        try 
        {   
            //this.debugGUI.addText("thread running");
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            while(running)
            {
                Message response = (Message)in.readObject();      
                //System.out.println();
                if (this instanceof OutputClient)
                {
                    OutputClient c = (OutputClient)this;
                    System.out.println("Message received: " + this.getClass() + ", " + response);
                }
                if (response instanceof Response)
                    parseResponse((Response)response);
                else if(response instanceof EventNotification)
                    parseEventNotification((EventNotification)response);
            } // while running
        } // try
        catch (IOException | ClassNotFoundException ex) {
            System.out.println("error in run");
            Logger.getLogger(WaiterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
                catch (Exception ex) {
            System.out.println("error thrown here!");
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("exiting thread");
    }
    
    public Client(RegisterClientRequest.ClientType type)
    {
        this.type = type;
        InetAddress serverAddress = null;
        try
        {
            serverPort = StartServer.getPORT_NUMBER();
            serverAddress  = InetAddress.getByName(null);
            client = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(client.getOutputStream());
            this.debugGUI = null;
            this.responseThread = null;
            System.out.println("outputstream made");
        } // try // try
        catch (IOException e)
        {
            System.out.println("Could not connect to server");
        } // catch
 // catch
            this.address = client.getLocalAddress();
            this.serverAddress = serverAddress;
    }
    
        /**
     * @return The output stream of the client.
     */
   public ObjectOutputStream getOutputStream()
   {
       return out;
   }
   
    private void registerClientResponse(RegisterClientResponse resp)
    {
        System.out.println("parse register client response");
        RegisterClientResponse rResponse = (RegisterClientResponse)resp;
        RegisterClientRequest req = (RegisterClientRequest)rResponse.getRequest();
            
        if (!rResponse.hasPermission())
        {
            debugGUI.addText("A client already exists!");
            System.exit(0);                               
        } // if
        else System.out.println("Client successfully registered as: " + req);        
    } // regClientResp
      
   
    public void parseResponse(Response response) throws IOException, ClassNotFoundException
    {
        if (response instanceof LeaveResponse)
        {
            LeaveResponse leaveR = (LeaveResponse)response;
            if (leaveR.hasPermission())
                System.exit(0);
        } // if
        if (response instanceof RegisterClientResponse)
            registerClientResponse((RegisterClientResponse)response);
        
    }
   
    public void parseEventNotification(EventNotification evntNfn) throws IOException, ClassNotFoundException
    {
        
    }
   
    public String sortTimeSyntax(int time)
    {        
        if (time == 0) return "00";
        else if (time > 0 && time <= 9)
            return "0" + time;
        else return time + "";
        
    } // sortTimeSyntax

    public String timeToString(int hours, int minutes)
    {
        return sortTimeSyntax(hours) + ":" + sortTimeSyntax(minutes);
    }
   
    
    public static Client makeClient(ClientType clientType)
    {
        Client till;
        
        switch (clientType)
        {
            case WAITER:
                till = new WaiterClient(clientType);

                break;
            case TILL:
                till = new TillClient(clientType);
                break;       
            default:
                till = new OutputClient(clientType);
        }
            till.debugGUI = OutputGUI.makeGUI(till);        
            till.responseThread = new Thread(till);
            till.responseThread.start();
            till.registerClient();
            return till;
    } // makeClient
    
    
    public ClientType getType()
    {
        return type;
    }
    
    public final boolean registerClient()
    {
        RegisterClientRequest rKitchenReq = new RegisterClientRequest(
                this.address,
                this.serverAddress,
                type);
        return writeMessage(rKitchenReq);
    } // registerClient
    
    public final boolean leaveRequest()
    {
        LeaveRequest leaveRequest = new LeaveRequest(
                this.address,
                this.serverAddress
        );
        return writeMessage(leaveRequest);
    } // registerClient
    
    public <M extends Message> boolean writeMessage(M message)
    {
        try 
        {           
            out.writeObject(message);
            out.reset();
        } 
        catch (IOException ex) 
        {
            System.out.println("failed to send message:" + message);
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return true;
    }

}
