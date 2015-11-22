package com.mike.client;

import com.mike.message.EventNotification.EventNotification;
import com.mike.message.EventNotification.TableStatusEvtNfn;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Request.TabRequest;
import com.mike.message.Request.TableStatusRequest;
import com.mike.message.Response.Response;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;
import com.mike.message.Table;
import com.mike.message.Table.TableStatus;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mbbx9mg3
 */
@Component
public abstract class UserClient extends Client 
{
    public final Object lock = new Object();
    public ArrayList<Table.TableStatus> tableStatuses = null; // temp variable

    public UserClient(ClientType type) 
    {
        super();
        //super(type);
    }
    
    protected void parseTableStatusResponse(TableStatusResponse resp)
    {
        System.out.println("table status response");
       System.out.println(resp.getStatuses());
       
       TableStatusRequest req = (TableStatusRequest)resp.getRequest();
       

       
        synchronized(lock)
        {
            if (req.getTableList().size() == 1 && req.getTableList().get(0) == -1)
                this.tableStatuses = resp.getStatuses();
            else
                setTableStatuses(resp);
            lock.notifyAll();
        }           
    }
    
    public abstract void parseTabResponse(TabResponse resp);
    
    
    @Override
    public void parseResponse(Response response) throws IOException, ClassNotFoundException 
    {
        super.parseResponse(response);
        


        if (response instanceof TabResponse) 
            parseTabResponse((TabResponse)response);
        if (response instanceof TableStatusResponse) 
            parseTableStatusResponse((TableStatusResponse)response);
              
    } // parseResponse

    /**
     * @param resp
     */
   public void setTableStatuses(TableStatusResponse resp)
   {
       ArrayList<Table.TableStatus>  x = resp.getStatuses();
       TableStatusRequest req = (TableStatusRequest)resp.getRequest();
       
       for (int i = 0; i < req.getTableList().size(); i++)
           tableStatuses.set(req.getTableList().get(i),
                   x.get(i));
      
       
   }
   
   protected abstract void parseTableStatusEvtNfn(TableStatusEvtNfn event);
   
   
   @Override
   public void parseEventNotification(EventNotification evntNfn) throws IOException, ClassNotFoundException
   {
        super.parseEventNotification(evntNfn);
        if (evntNfn instanceof TableStatusEvtNfn)
            parseTableStatusEvtNfn((TableStatusEvtNfn)evntNfn);                     
   } // parseEventNot
   
   public Object getLock()
   {
       return lock;
   }
   
    public boolean sendTableStatusEventNotification(int tableNumber, 
                                                     TableStatus status)
    {
        TableStatusEvtNfn newEvt = new TableStatusEvtNfn(address,
            serverAddress,
            tableNumber, status);
     //   return writeMessage(newEvt);  
    return true;
    }
    
    public boolean sendTabRequest(int table)
    {
        TabRequest tabStatusRequest = new TabRequest(this.address,
            this.serverAddress,
            table);        
       // return writeMessage(tabStatusRequest);
        return true;
    }
   
}
