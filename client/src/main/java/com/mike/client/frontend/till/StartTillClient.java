package com.mike.client.frontend.till;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.SocketUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michaelg on 06/09/2016.
 */
public class StartTillClient {
	public static void main(String[] args) throws InterruptedException
	{
		GenericXmlApplicationContext context = setupContext();
		TillClientController tillClientController = (TillClientController) context.getBean("tillClientController");
		tillClientController.init();
	} // main

	private static GenericXmlApplicationContext setupContext() {
		final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

		System.out.print("Detect open server socket...");
		int availableServerSocket = SocketUtils.findAvailableTcpPort();

		final Map<String, Object> sockets = new HashMap<String, Object>();
		sockets.put("availableServerSocket", availableServerSocket);

		final MapPropertySource propertySource = new MapPropertySource("sockets", sockets);

		context.getEnvironment().getPropertySources().addLast(propertySource);

		System.out.println("using port " + context.getEnvironment().getProperty("availableServerSocket"));

		context.load("/META-INF/till-client-context.xml");
		context.registerShutdownHook();
		context.refresh();

		return context;
	}
}