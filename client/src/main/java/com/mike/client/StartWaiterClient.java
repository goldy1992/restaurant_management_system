package com.mike.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.SocketUtils;

public class StartWaiterClient {
	   /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
    	GenericXmlApplicationContext context = setupContext();
        WaiterClientController waiterClientController = (WaiterClientController)context.getBean("waiterClientController");
        waiterClientController.init();
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

		context.load("/META-INF/client-context.xml");
		context.registerShutdownHook();
		context.refresh();

		return context;
	}
    
}
