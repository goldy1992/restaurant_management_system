package com.mike.server;

import com.mike.message.Table;
import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Request.TableStatusRequest;
import com.mike.message.Response.RegisterClientResponse;
import com.mike.message.Response.TableStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by michaelg on 24/11/2015.
 */
@Component
public class MessageParser {

	@Autowired
	private Server server;

	@ServiceActivator(inputChannel="messageRegisterClientRequestChannel",  outputChannel="messageResponseChannel")
	public RegisterClientResponse parseRegisterClientRequest(RegisterClientRequest request,  @Headers Map<String, Object> headerMap)
	{
		System.out.println("hit register client");
		RegisterClientResponse response = new RegisterClientResponse(request);
		String ipAddress = (String)headerMap.get(IpHeaders.CONNECTION_ID);

		response.setPermission(server.registerClient(request.getClientType(), ipAddress));
		return response;
	}
	
	@ServiceActivator(inputChannel="messageTableStatusRequestChannel",  outputChannel="messageResponseChannel")
	public TableStatusResponse parseTableStatusRequest(TableStatusRequest request)
	{
		
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
}
