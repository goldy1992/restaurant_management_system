/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.EventNotification.*;
import Message.Message;
import Message.Request.RegisterClientRequest;
import Message.Request.Request;
import Message.Request.TableStatusRequest;
import Message.Response.*;
import Message.Table;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbbx9mg3
 */
public class TillClient extends UserClient
{
    public TillGUI gui = null;
    public TillClient(RegisterClientRequest.ClientType  type) throws IOException
    {
        super(type);
    } // constructor
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {   
        TillClient myClient = null;
        try 
        {
            myClient = Client.makeClient(RegisterClientRequest.ClientType.TILL);
            
                        ArrayList<Integer> tables = new ArrayList<>();
            // add null because there's no table zero
            tables.add(-1);
                        TableStatusRequest request = new TableStatusRequest(
                InetAddress.getByName(
                    myClient.client.getLocalAddress().getHostName()),
                InetAddress.getByName(
                    myClient.serverAddress.getHostName() ),
                Message.generateRequestID(),
                Request.RequestType.TABLE_STATUS,
                tables);
            myClient.getOutputStream().writeObject(request);  
            myClient.gui = new TillGUI(myClient);
            myClient.gui.setTitle("Till");
            myClient.gui.setVisible(true);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(TillClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("end of till client");
    } // main
      
    /**
     *
     * @param response
     * @return 
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */  
    
    public ArrayList<Table.TableStatus> getTableStatuses()
    {
        return tableStatuses;
    }

    @Override
    protected void parseTableStatusEvtNfn(TableStatusEvtNfn event) 
    {

        if (tableStatuses != null)
        {
                    System.out.println("notification for table status received");
            this.tableStatuses.set(event.getTableNumber(), event.getTableStatus());
            if (this.gui.getMenu() != null)
            {
                System.out.println("updating buttons");
                this.gui.getMenu().updateButtons((ArrayList<Table.TableStatus>)tableStatuses.clone());
            }
            else
              System.out.println("gui is null");
        }
    } //  parseTableStatusEvtNfn
    

       
    
} // class

    

