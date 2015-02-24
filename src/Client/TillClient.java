/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.EventNotification.*;
import Message.Request.RegisterClientRequest;
import Message.Request.TableStatusRequest;
import Message.Response.*;
import Message.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbbx9mg3
 */
public class TillClient extends UserClient
{
    
    public TillClient(RegisterClientRequest.ClientType  type) throws IOException
    {
        super(type);
    } // constructor
    
    private ArrayList<Table.TableStatus> tableStatuses = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {   
        TillClient myClient = null;
        try 
        {
            myClient = Client.makeClient(RegisterClientRequest.ClientType.TILL);
            TillGUI gui = new TillGUI(myClient);
            gui.setTitle("Till");
            gui.setVisible(true);
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
            this.tableStatuses.set(event.getTableNumber(), event.getTableStatus());   
    } //  parseTableStatusEvtNfn
    

       
    
} // class

    

