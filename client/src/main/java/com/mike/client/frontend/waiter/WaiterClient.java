package com.mike.client.frontend.waiter;

import com.mike.client.frontend.UserClient;
import com.mike.client.frontend.SelectTableMenu.SelectTableController;
import com.mike.message.Request.RegisterClientRequest.ClientType;
import com.mike.message.Table;
import org.springframework.stereotype.Component;

import java.util.Map;

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

    


    
    
} // MyClientSocketClass
