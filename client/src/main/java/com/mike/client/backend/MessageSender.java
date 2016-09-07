package com.mike.client.backend;

import com.mike.item.Tab;
import com.mike.message.EventNotification.TabUpdateNfn;
import com.mike.message.EventNotification.TableStatusEvtNfn;
import com.mike.message.Request.LeaveRequest;
import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Request.TabRequest;
import com.mike.message.Request.TableStatusRequest;
import com.mike.message.Request.databaseRequest.Query;
import com.mike.message.Request.databaseRequest.Update;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;
import com.mike.message.Response.databaseResponse.QueryResponse;
import com.mike.message.Response.databaseResponse.UpdateResponse;
import com.mike.message.Table.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.PollableChannel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelg on 09/12/2015.
 */
public class MessageSender {

	@Autowired
	private PollableChannel registerClientResponseChannel;
	
	@Autowired
	private PollableChannel tableStatusResponseChannel;
	
	@Autowired
	private PollableChannel tabResponseChannel;
	
	@Autowired
	private PollableChannel dbQueryResponseChannel;

	@Autowired
	private PollableChannel dbUpdateResponseChannel;
	
	public void setDbQueryResponseChannel(PollableChannel dbQueryResponseChannel) { this.dbQueryResponseChannel = dbQueryResponseChannel; }
	public void setTabResponseChannel(PollableChannel tabResponseChannel) { this.tabResponseChannel = tabResponseChannel; }
	public void setTableStatusResponseChannel(PollableChannel tableStatusResponseChannel) { this.tableStatusResponseChannel = tableStatusResponseChannel; }
	public void setSendGateway(SendGateway sendGateway) { this.sendGateway = sendGateway; }
	public void setRegisterClientResponseChannel(PollableChannel registerClientResponseChannel) { this.registerClientResponseChannel = registerClientResponseChannel; }

	public PollableChannel getTableStatusResponseChannel() { return tableStatusResponseChannel; }

	@Autowired
	SendGateway sendGateway;



	public TableStatusResponse sendTableStatusRequest(ArrayList<Integer> tables) {
		TableStatusRequest request = new TableStatusRequest(tables);
		sendGateway.send(request);
		TableStatusResponse response =(TableStatusResponse) tableStatusResponseChannel.receive().getPayload();
		return response;
	}

	public final RegisterClientResponse registerClient(RegisterClientRequest.ClientType type) {
		RegisterClientRequest rKitchenReq = new RegisterClientRequest(type);
		sendGateway.send(rKitchenReq);
		System.out.println("message sent");
		
		RegisterClientResponse registerClientResponse = (RegisterClientResponse)registerClientResponseChannel.receive().getPayload();
        System.out.println("parse register client response");

        return registerClientResponse;
	} // registerClient

	public final boolean leaveRequest() {
		LeaveRequest leaveRequest = new LeaveRequest();
		sendGateway.send(leaveRequest);
		return true;
	} 
	
	public TabResponse sendTabRequest(int tableNumber) {
		TabRequest tabStatusRequest = new TabRequest(tableNumber);        
		sendGateway.send(tabStatusRequest);
		TabResponse tabResponse = (TabResponse)tabResponseChannel.receive().getPayload();
		return tabResponse;
	}

	public void sendTableStatusEventNotification(int tableSelected, TableStatus selectedTableStatus) {
		TableStatusEvtNfn tableStatusEvtNfn = new TableStatusEvtNfn(tableSelected, selectedTableStatus);
		sendGateway.send(tableStatusEvtNfn);
	}

	public void sendTabUpdateNotification(Tab tab) {
		TabUpdateNfn tabUpdateNfn = new TabUpdateNfn(tab);
		sendGateway.send(tabUpdateNfn);
	}

	public List query(String query) {
		QueryResponse response = sendDbQuery(query);
		return response.getResultSet();
	}

	public <T extends Serializable> UpdateResponse update(T item) {
		Update update = new Update(item);
		sendGateway.send(update);
		return (UpdateResponse)dbUpdateResponseChannel.receive().getPayload();
	}

	public QueryResponse sendDbQuery(String query) {
		Query queryRequest = new Query(query);
		System.out.println("sending db query");
		sendGateway.send(queryRequest);
		return (QueryResponse)dbQueryResponseChannel.receive().getPayload();
	}

	public void sendOrder(Tab updatedTab, Tab tabWithNewItemsOnly) {

//            TabUpdateNfn newEvt = new TabUpdateNfn(InetAddress.getByName(
//                parentClient.client.getLocalAddress().getHostName()),
//                InetAddress.getByName(parentClient.serverAddress.getHostName()),
//                 this.oldTab);
//            out.reset();
//            out.writeObject(newEvt);

		// send the new items to the bar or kitchen respectively
//			if (this.newTab.getDrinks().size() > 0)
		{
//                NewItemNfn newEvt1 = new NewItemNfn(InetAddress.getByName(
//                    parentClient.client.getLocalAddress().getHostName()),
//                    InetAddress.getByName(parentClient.serverAddress.getHostName()),
//                    Item.Type.DRINK,
//                    this.newTab.getDrinks(),
//          //          newTab.getTable());
//                    out.reset();
//                    out.writeObject(newEvt1);
		} // if

		//		if (this.newTab.getFood().size() > 0)
		{
//                NewItemNfn newEvt1 = new NewItemNfn(InetAddress.getByName(
//                    parentClient.client.getLocalAddress().getHostName()),
//                    InetAddress.getByName(parentClient.serverAddress.getHostName()),
//                    Item.Type.FOOD,
//                    this.newTab.getFood(),
//                    newTab.getTable());
//               out.reset();
//                out.writeObject(newEvt1);
		} // if


	}


}
