package com.mike.server;

import com.mike.message.Request.RegisterClientRequest;
import com.mike.server.database.InitialiseDatabase;
import com.mike.message.Table;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Goldy
 */
public class Server
{
    private HashSet<String> waiterClient;
    private HashSet<String> tillClient;
    private String barClient = null;
    private String kitchenClient = null;
    private HashMap<Integer, Table> tables;
    
    public Server(ServerSocket socket, HashSet<String> waiterClient,
                HashSet<String> tillClient) throws IOException {
        this.setWaiterClient(waiterClient);
        this.setTillClient(tillClient);
        
    } // myserver
    
    public Server() {         }


	public HashSet<String> getWaiterClient() {
		return waiterClient;
	}

	public void setWaiterClient(HashSet<String> waiterClient) {
		this.waiterClient = waiterClient;
	}

	public HashSet<String> getTillClient() {
		return tillClient;
	}

	public void setTillClient(HashSet<String> tillClient) {
		this.tillClient = tillClient;
	}

	public String getBarClient() {
		return barClient;
	}

	public void setBarClient(String barClient) {
		this.barClient = barClient;
	}

	public String getKitchenClient() {
		return kitchenClient;
	}

	public void setKitchenClient(String kitchenClient) {
		this.kitchenClient = kitchenClient;
	}

	public HashMap<Integer, Table> getTables() {
		return tables;
	}

	public void setTables(HashMap<Integer, Table> tables) {
		this.tables = tables;
	}

	public boolean registerClient(RegisterClientRequest.ClientType clientType, String address) {
		switch(clientType)
		{
			case BAR:
				if (this.barClient == null) {
					barClient = address;
					return true;
				}
				return false;
			case KITCHEN:
				if (this.kitchenClient == null) {
					kitchenClient = address;
					return true;
				}
				return false;
			case WAITER:
				return waiterClient.add(address);
			case TILL:
				return tillClient.add(address);
			default: return false;
		} // switch
	}
	
} // MySocket class
