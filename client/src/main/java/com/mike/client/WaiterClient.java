package com.mike.client;

import com.mike.client.SelectTableMenu.SelectTableController;
import com.mike.message.EventNotification.TableStatusEvtNfn;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;
import java.util.*;
import com.mike.message.Table;
import com.mike.message.Table.TableStatus;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

/**
 *
 * @author Goldy
 */
@Component
public class WaiterClient extends UserClient
{
    public SelectTableController selectTable;  
    
    public WaiterClient() {
        super(ClientType.WAITER);
    }
    public WaiterClient(ClientType  type)
    {
        super(type);
    } // constructor
	private Map<Integer,Table> tableMap;

 
    
    @Override
    public void parseTabResponse(TabResponse resp)
    {
        //debugGUI.addText("Executing TabResponse's onreceiving");
        synchronized(selectTable)
        {
            selectTable.setTab(resp.getTab());
            selectTable.setTabReceived(true);      
            selectTable.tabLock.notifyAll();
        } // synchronized        
    }
        

    @Override
    protected void parseTableStatusEvtNfn(TableStatusEvtNfn event) 
    {
        selectTable.setTableStatus(event.getTableNumber(), event.getTableStatus());      
    }

    public void setTableStatuses(Map<Integer, TableStatus> statusMap) {
		if (this.tableMap == null) {
			tableMap = new HashMap<>();
		}
		for (Integer t : statusMap.keySet()) {
			if (!tableMap.containsKey(t)) {
				Table table = new Table(t);
				table.setTableStatus(statusMap.get(t));
				tableMap.put(t, table);
			} else {
				tableMap.get(t).setTableStatus(statusMap.get(t));
			}
			tableMap.get(t).setTableStatus(statusMap.get(t));
		}    	
    }
    


    
    
} // MyClientSocketClass
