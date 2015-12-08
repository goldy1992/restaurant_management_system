package com.mike.server;

import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Response.RegisterClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

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
}
