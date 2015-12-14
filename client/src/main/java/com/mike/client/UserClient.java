package com.mike.client;

import com.mike.message.EventNotification.TableStatusEvtNfn;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Request.TabRequest;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;
import com.mike.message.Table;
import com.mike.message.Table.TableStatus;

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
        super(type);
    }
    
    
    


    /**
     * @param resp
     */
   public void setTableStatuses(TableStatusResponse resp)
   {
//       ArrayList<Table.TableStatus>  x = resp.getStatuses();
//       TableStatusRequest req = (TableStatusRequest)resp.getRequest();
//       
//       for (int i = 0; i < req.getTableList().size(); i++)
//           tableStatuses.set(req.getTableList().get(i),
//                   x.get(i));
      
       
   }
   
   
   

   
   public Object getLock()
   {
       return lock;
   }
   
    public boolean sendTableStatusEventNotification(int tableNumber, 
                                                     TableStatus status)
    {
        TableStatusEvtNfn newEvt = new TableStatusEvtNfn(tableNumber, status);
     //   return writeMessage(newEvt);  
    return true;
    }
    

   
}
