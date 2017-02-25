package com.mike.client.frontend.waiter;

import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.SocketUtils;

import java.util.HashMap;
import java.util.Map;

public class StartWaiterClient {

	final static Logger logger = Logger.getLogger(StartWaiterClient.class);
	
	   /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
    	GenericXmlApplicationContext context = setupContext();
        WaiterClientController waiterClientController = (WaiterClientController) context.getBean("waiterClientController");
        waiterClientController.init();
    } // main
    
    private static GenericXmlApplicationContext setupContext() {
		final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

		logger.info("Detect open server socket...");
		int availableServerSocket = SocketUtils.findAvailableTcpPort();

		final Map<String, Object> sockets = new HashMap<String, Object>();
		sockets.put("availableServerSocket", availableServerSocket);

		final MapPropertySource propertySource = new MapPropertySource("sockets", sockets);

		context.getEnvironment().getPropertySources().addLast(propertySource);

		logger.info("using port " + context.getEnvironment().getProperty("availableServerSocket"));

		context.load("/META-INF/waiter-client-context.xml");
		context.registerShutdownHook();
		context.refresh();

		return context;
	}
    
}
