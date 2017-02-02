package com.mike.client;

import com.mike.client.frontend.SelectTableMenu.View.SelectTableView;
import com.mike.client.frontend.waiter.WaiterClientController;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.SocketUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class WaiterClientEndToEndTest {

    private ProcessBuilder pb;
    private Process p;
    File outputFile;
    File errorFile;
    SelectTableView selectTableView;
    WaiterClientController waiterClientController;
    GenericXmlApplicationContext context;
    OutputStream os;
    @Before
    public void setup() throws IOException, ClassNotFoundException, InterruptedException {
        deployServer();
        context = setupContext();

        waiterClientController = (WaiterClientController) context.getBean("waiterClientController");
        waiterClientController.init();
        selectTableView = waiterClientController.getSelectTableController().view;
    }

    private void deployServer() throws IOException {
        SecureRandom random = new SecureRandom();
        String randomString = new BigInteger(130, random).toString(32);
        outputFile = new File("output");
        errorFile = new File("error");
        pb = new ProcessBuilder();
        pb.directory(new File(System.getProperty("user.dir")));
        pb.command(new String[]{"cmd.exe", "/c", "cd", "..", "&", "mvn", "clean", "install", "-DskipTests=true", "&", "cd", "server", "&", "mvn", "exec:java", "-Dexec.mainClass=\"com.mike.server.StartServer\"", "-Dexec.args=\"" +  randomString + "\""});
        pb.redirectOutput(outputFile);
        pb.redirectError(errorFile);

        p = pb.start();
        os = p.getOutputStream();

        boolean deploymentComplete = false;
        while(!deploymentComplete) {
            String sCurrentLine;
            BufferedReader br = new BufferedReader(new FileReader(outputFile));

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains(randomString)) {
                    deploymentComplete = true;
                }
            }
            br.close();
            br = new BufferedReader(new FileReader(errorFile));

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains(randomString)) {
                    deploymentComplete = true;
                }
            }
            br.close();
        }
    } // deployServer

    @After
    public void tearDown() throws IOException {
        String exit = "exit";
        os.write(exit.getBytes());
        os.flush();
        os.close();
        outputFile.delete();
        errorFile.delete();
        p.destroy();
        System.out.println("p alive: " + p.isAlive());
        context.destroy();
    }

    private void openTable() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                selectTableView.getOpenTable().doClick();
            }
        });
        t.start();

        Pause.pause(new Condition("waiting for view to appear") {
            @Override
            public boolean test() {
                if (waiterClientController.getSelectTableController().getMenuController().getView() == null) {
                    return false;
                }

                if (!waiterClientController.getSelectTableController().getMenuController().getView().isVisible()) {
                    return false;
                }
                return true;
            }
        });
    }

    @Test
    public void changeTableStatusTest() throws InterruptedException, AWTException, InvocationTargetException {
        // GIVEN: the user is on the select table menu and all of the tables a currently free
        // WHEN: the user chooses to open table 8
        selectTableView.getTableButtons()[8].doClick();
        // THEN: a message appears saying: Would you like to open Table 8
        assertThat(selectTableView.getOutputLabel().getText(), CoreMatchers.containsString("Would you like to open Table 8"));
        // WHEN: the table is opened
        openTable();
        assertThat(selectTableView.getTableButtons()[8].getText(), CoreMatchers.containsString("Table in Use"));
        assertThat(waiterClientController.getSelectTableController().getMenuController().getView().isVisible(), CoreMatchers.is(true));
        // AND: the order is sent
        getButton("Send Order").doClick();
        // THEN: the status of the table is set to occupied
        assertThat(waiterClientController.getSelectTableController().getMenuController().getView().isVisible(), CoreMatchers.is(false));
        assertThat(selectTableView.getTableButtons()[8].getText(), CoreMatchers.containsString("Occupied"));
    }

    private JButton getButton(String buttonName) {
        ArrayList<JButton> buttons = waiterClientController.getSelectTableController().getMenuController().getView().getButtons();
        JButton sendOrderButton = null;
        for (JButton jb : buttons) {
            if (jb.getText().equals(buttonName)) {
                sendOrderButton = jb;
            }
        }
        return sendOrderButton;
    }

    @Test
    public void secondTest() {
        Pattern p = Pattern.compile("Table 8");
        Matcher m = p.matcher("<html>Table 8<br>Free</html>");

        assertTrue(m.find());
    }

    private GenericXmlApplicationContext setupContext() {
        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

        System.out.print("Detect open server socket...");
        int availableServerSocket = SocketUtils.findAvailableTcpPort();

        final Map<String, Object> sockets = new HashMap<String, Object>();
        sockets.put("availableServerSocket", availableServerSocket);

        final MapPropertySource propertySource = new MapPropertySource("sockets", sockets);

        context.getEnvironment().getPropertySources().addLast(propertySource);

        System.out.println("using port " + context.getEnvironment().getProperty("availableServerSocket"));

        context.load("/META-INF/waiter-client-context.xml");
        context.registerShutdownHook();
        context.refresh();

        return context;
    }

}