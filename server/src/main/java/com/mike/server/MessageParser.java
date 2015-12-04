package com.mike.server;

import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Response.RegisterClientResponse;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

/**
 * Created by michaelg on 24/11/2015.
 */
@Component
public class MessageParser {

	@ServiceActivator(inputChannel="messageRegisterClientRequestChannel", outputChannel="messageResponseChannel")
	public RegisterClientResponse parseRegisterClientRequest(RegisterClientRequest request)
	{
		System.out.println("hit register client");
		RegisterClientResponse response = new RegisterClientResponse(request);
		boolean hasPermission;

//		switch(response.getClientType())
//		{
//			case BAR:
//				hasPermission = server.getBarClient() == null;
//				response.setPermission(hasPermission);
//				System.out.println("dealing with bar req");
//				if (hasPermission)
//				{
//					server.setBarClient(client);
//				}
//				break;
//			case KITCHEN:
//				hasPermission = server.getKitchenClient() == null;
//				response.setPermission(hasPermission);
//				System.out.println("dealing with kitchen req");
//				if (hasPermission)
//				{
//					server.setKitchenClient(client);
//				}
//				break;
//			case WAITER:
//				response.setPermission(true);
//				break;
//			case TILL:
//				response.setPermission(true);
//				break;
//			default: break;
//		} // switch

		return response;
	}
}
