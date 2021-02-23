package com.github.goldy1992.rms.client.frontend.output;

import com.github.goldy1992.rms.message.Request.RegisterClientRequest;
import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.SocketUtils;

import java.util.HashMap;
import java.util.Map;

import static com.github.goldy1992.rms.message.Request.RegisterClientRequest.ClientType.BAR;
import static com.github.goldy1992.rms.message.Request.RegisterClientRequest.ClientType.KITCHEN;

public class StartOutputClient {

    final static Logger logger = Logger.getLogger(StartOutputClient.class);
    
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        RegisterClientRequest.ClientType clientType = null;
        try {
            String val = "bar";
            if (args.length > 0) {
                val = args[0];
            }
            switch(val) {
                case "bar": clientType = BAR; break;
                case "kitchen": clientType = KITCHEN; break;
                default: logger.info("invalid argument"); System.exit(0);
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

        logger.info("Detect open server socket...");
        int availableServerSocket = SocketUtils.findAvailableTcpPort();

        final Map<String, Object> sockets = new HashMap<String, Object>();
        sockets.put("availableServerSocket", availableServerSocket);

        final MapPropertySource propertySource = new MapPropertySource("sockets", sockets);

        context.getEnvironment().getPropertySources().addLast(propertySource);

        logger.info("using port " + context.getEnvironment().getProperty("availableServerSocket"));

        context.load("/META-INF/output-client-context.xml");
        context.registerShutdownHook();
        context.refresh();

        return context;
    }

}
