package com.mike.client.frontend.output;

import com.mike.message.Request.RegisterClientRequest;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.SocketUtils;

import java.util.HashMap;
import java.util.Map;

import static com.mike.message.Request.RegisterClientRequest.ClientType.BAR;
import static com.mike.message.Request.RegisterClientRequest.ClientType.KITCHEN;

public class StartOutputClient {
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        RegisterClientRequest.ClientType clientType = null;
        try {
            switch(args[0]) {
                case "bar": clientType = BAR; break;
                case "kitchen": clientType = KITCHEN; break;
                default: System.out.println("invalid argument"); System.exit(0);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        GenericXmlApplicationContext context = setupContext();
        OutputClientController outputClientController = (OutputClientController) context.getBean("outputClientController");
        outputClientController.init(clientType);
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

        context.load("/META-INF/output-client-context.xml");
        context.registerShutdownHook();
        context.refresh();

        return context;
    }

}
