package com.mike.server;

import com.mike.message.EventNotification.EventNotification;
import com.mike.message.EventNotification.TabUpdateNfn;
import com.mike.message.EventNotification.TableStatusEvtNfn;
import com.mike.message.Request.LeaveRequest;
import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Request.TabRequest;
import com.mike.message.Request.TableStatusRequest;
import com.mike.message.Request.databaseRequest.Query;
import com.mike.message.Request.databaseRequest.Update;
import com.mike.message.Response.LeaveResponse;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.TabResponse;
import com.mike.message.Response.TableStatusResponse;
import com.mike.message.Response.databaseResponse.QueryResponse;
import com.mike.message.Response.databaseResponse.UpdateResponse;
import com.mike.message.Table;
import com.mike.server.database.DatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michaelg on 24/11/2015.
 */
@Component
public class MessageParser {

	@Autowired
	private DatabaseConnector dbCon;
	
	@Autowired
	private Server server;

	@Autowired
	private SendGateway sendGateway;

	@ServiceActivator(inputChannel="messageRegisterClientRequestChannel",  outputChannel="messageResponseChannel")
	public RegisterClientResponse parseRegisterClientRequest(RegisterClientRequest request,  @Headers Map<String, Object> headerMap) {
		System.out.println("hit register client");
		RegisterClientResponse response = new RegisterClientResponse(request);
		String ipAddress = (String)headerMap.get(IpHeaders.CONNECTION_ID);

		response.setPermission(server.registerClient(request.getClientType(), ipAddress));
		return response;
	}
	
	@ServiceActivator(inputChannel="messageTableStatusRequestChannel",  outputChannel="messageResponseChannel")
	public TableStatusResponse parseTableStatusRequest(TableStatusRequest request) {
		
		Map<Integer, Table.TableStatus> tableStatuses = new HashMap<>();
		
		if (request.getTableList().isEmpty()) {
			System.out.println("tablesize " + server.getTables().keySet());
			for (Integer i :  server.getTables().keySet()) {
				tableStatuses.put(i, server.getTables().get(i).getStatus());
			}
		} else {
			for (Integer i :  request.getTableList()) {
				tableStatuses.put(i, server.getTables().get(i).getStatus());
			}			
		}
		TableStatusResponse response = new TableStatusResponse(request, tableStatuses);
		return response;
	}
	
	@ServiceActivator(inputChannel="messageTabRequestChannel", outputChannel="messageResponseChannel")
	public TabResponse parseTabRequest(TabRequest tabRequest) {
		
		TableStatusEvtNfn tableStatusEvtNfn = new TableStatusEvtNfn(tabRequest.getTabNumber(), Table.TableStatus.IN_USE);
		server.getTables().get(tabRequest.getTabNumber()).setTableStatus(Table.TableStatus.IN_USE);
		for (String clients : server.getWaiterClient()) {
			MessageHeaders mh = new MessageHeaders(null);
			Message<TableStatusEvtNfn> m = MessageBuilder.createMessage(tableStatusEvtNfn, mh);
			Message<TableStatusEvtNfn> mSend = MessageBuilder.fromMessage(m).setHeader(IpHeaders.CONNECTION_ID, clients).build();
			sendGateway.send(mSend);
		}
		TabResponse tabResponse = new TabResponse(tabRequest);
		tabResponse.setTab(server.getTables().get(tabRequest.getTabNumber()).getCurrentTab());
		return tabResponse;
	}

	@ServiceActivator(inputChannel="messageLeaveRequestChannel", outputChannel="messageResponseChannel")
	public LeaveResponse parseLeaveRequest(LeaveRequest leaveRequest, @Headers Map<String, Object> headerMap) {
		LeaveResponse response = new LeaveResponse(leaveRequest);
		String ipAddress = (String)headerMap.get(IpHeaders.CONNECTION_ID);
		server.removeClient(ipAddress);
		response.setPermission(true);
		return response;
	}
	
	@ServiceActivator(inputChannel="messageQueryChannel", outputChannel="messageResponseChannel")
	public QueryResponse parseQuery(Query query) {
		System.out.println("performing query");
		List resultSet = dbCon.query(query.getQuery(), null);
		QueryResponse queryResponse = new QueryResponse(query, resultSet);
		return queryResponse;
	}

	@ServiceActivator(inputChannel="messageUpdateChannel", outputChannel="messageResponseChannel")
	public UpdateResponse parseUpdate(Update update) {
		System.out.println("performing update");
		return new UpdateResponse(update, dbCon.update(update.getItem()));
	}

	@ServiceActivator(inputChannel="messagetableStatusEventNotificationChannel", outputChannel="messageResponseChannel")
	public void parseTableStatusEventNotification(TableStatusEvtNfn tableStatusEvtNfn) {
		server.getTables().get(tableStatusEvtNfn.getTableNumber()).setTableStatus(tableStatusEvtNfn.getTableStatus());
		sendNotification(tableStatusEvtNfn);
	}

	@ServiceActivator(inputChannel="messagetabUpdateNotificationChannel", outputChannel="messageResponseChannel")
	public void parseTabUpdateEventNotification(TabUpdateNfn tabUpdateNfn){
		int tabNumber = tabUpdateNfn.getTab().getTabNumber();
		server.getTables().get(tabNumber).updateTab(tabUpdateNfn.getTab());
	}

	@ServiceActivator(inputChannel="customErrorChannel")
	public void parseError(Message message, @Headers Map<String, Object> headerMap) {

		System.out.println("hit error message");
	}

	
	public void setSendGateway(SendGateway sendGateway) { this.sendGateway = sendGateway; }
	
	public void setServer(Server server) { this.server = server; }

	private void sendNotification(EventNotification eventNotification) {
		for (String clients : server.getClients()) {
			MessageHeaders mh = new MessageHeaders(null);
			Message<EventNotification> m = MessageBuilder.createMessage(eventNotification, mh);
			Message<EventNotification> mSend = MessageBuilder.fromMessage(m).setHeader(IpHeaders.CONNECTION_ID, clients).build();
			sendGateway.send(mSend);
		}
	}

	public DatabaseConnector getDbCon() {
		return dbCon;
	}

	public void setDbCon(DatabaseConnector dbCon) {
		this.dbCon = dbCon;
	}
}
