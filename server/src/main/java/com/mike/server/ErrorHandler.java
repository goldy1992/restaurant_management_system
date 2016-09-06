package com.mike.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.stereotype.Component;

/**
 * Created by michaelg on 31/08/2016.
 */
@Component
public class ErrorHandler {

	@Autowired
	private Server server;

	@ServiceActivator(inputChannel = "errorChannel")
	public void handError(Message<MessageHandlingException> message) {
		System.out.println("reached error handler");
	}


	@ServiceActivator(inputChannel = "eventChannel")
	public void handleEvent(TcpConnectionExceptionEvent tcpConnectionExceptionEvent) {
		System.out.println("reached event handler");
		String ipAddress = tcpConnectionExceptionEvent.getConnectionId();
		server.removeClient(ipAddress);
	}

	public void setServer(Server server) { this.server = server; }
}
