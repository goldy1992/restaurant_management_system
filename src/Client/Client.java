package Client;

import Message.EventNotification.EventNotification;
import Message.Message;
import Message.Request.RegisterClientRequest;
import Message.Request.RegisterClientRequest.ClientType;
import Message.Request.Request;
import Message.Response.LeaveResponse;
import Message.Response.RegisterClientResponse;
import Message.Response.Response;
import Server.MyServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbbx9mg3
 */
public abstract class Client implements Runnable
{
    // localhost
    public final InetAddress serverAddress = InetAddress.getByName(null); 
    public final InetAddress address;
    public int serverPort;  
    public Socket client;
    public OutputGUI debugGUI;
    public Thread responseThread;    
    private ObjectOutputStream out = null;
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
    
    public Client(RegisterClientRequest.ClientType type) throws UnknownHostException, IOException
    {
        this.type = type;
        try
        {
            serverPort = MyServer.getLowBoundPortRange();
            client = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(client.getOutputStream());
            this.debugGUI = null;
            this.responseThread = null;
            System.out.println("outputstream made");
        } // try
        catch (IOException e)
        {
            System.out.println("Could not connect to server");
        } // catch
            this.address = client.getLocalAddress();
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
   
    
    @SuppressWarnings("unchecked")
    public static Client makeClient(ClientType clientType) throws IOException
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
    
    public static boolean startClient(Client c)
    {
        c.debugGUI = OutputGUI.makeGUI(c);
        c.responseThread = new Thread(c);
        c.responseThread.start();
        c.registerClient();        
        return true;
    }
    
    public RegisterClientRequest.ClientType getType()
    {
        return type;
    }
    
    public final void registerClient()
    {

        try
        {
        RegisterClientRequest rKitchenReq = new RegisterClientRequest(
            InetAddress.getByName(client.getLocalAddress().getHostName()),
            InetAddress.getByName(serverAddress.getHostName()),
            Request.RequestType.REGISTER_CLIENT,
            type);     
        System.out.println("writing object: " + rKitchenReq);
            getOutputStream().reset();
            getOutputStream().writeObject(rKitchenReq);           
            getOutputStream().reset();     
            System.out.println("written");
        } 
        catch (IOException ex) {
            System.out.println("error thrown here!");
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

                                    System.out.println("eXITING");
    } // REGISTERcLIENT
    


}
