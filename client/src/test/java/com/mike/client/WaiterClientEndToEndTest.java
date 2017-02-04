package com.mike.client;

import com.mike.client.frontend.MainMenu.View.*;
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
import java.awt.event.MouseEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
        getOperationButton("Send Order").doClick();
        // THEN: the status of the table is set to occupied
        assertThat(waiterClientController.getSelectTableController().getMenuController().getView().isVisible(), CoreMatchers.is(false));
        assertThat(selectTableView.getTableButtons()[8].getText(), CoreMatchers.containsString("Occupied"));
    }

    private JButton getOperationButton(String buttonName) {
        ArrayList<JButton> buttons = waiterClientController.getSelectTableController().getMenuController().getView().getButtons();
        JButton sendOrderButton = null;
        for (JButton jb : buttons) {
            if (jb.getText().equals(buttonName)) {
                sendOrderButton = jb;
            }
        }
        return sendOrderButton;
    }

    private void selectMenuItem(String buttonName, int quantity) {
        MenuView mv = waiterClientController.getSelectTableController().getMenuController().getView();

        mv.getCardPanel().getName();
        while (!mv.currentCard.getName().equals("MAIN_PAGE")) {
            MouseEvent me = new MouseEvent((Component)mv.getOutputTextPane(), MouseEvent.MOUSE_CLICKED, 0, // no modifiers
                    10, 10, // where: at (10, 10}
                    1, 1, false, 0);
            mv.getOutputTextPane().dispatchEvent(me);
        }

        MenuItemJButton buttonToSelect = null;

        for (MenuItemJButton mib: mv.getMenuItemButtons()) {
            if (mib.getText().equals(buttonName)) {
                buttonToSelect = mib;
                break;
            }
        }

        List menuCardPanelList = new ArrayList<MenuCardPanel>();

        for (MenuCardPanel mcp : mv.getCardPanelsList()) {
            if (mcp.getCardMenuItems().contains(buttonToSelect)) {
                MenuCardPanel currentPanel = mcp;
                while (null != currentPanel.getParentPanel()){
                    menuCardPanelList.add(currentPanel);
                    currentPanel = currentPanel.getParentPanel();
                }
                break;
            }
        }

        for (int i = menuCardPanelList.size() - 1; i >= 0; i-- ) {
            List<MenuCardLinkJButton> menuCardLinkJButtonList = mv.currentCard.getChildCardButtons();
            for (MenuCardLinkJButton mb : menuCardLinkJButtonList) {
                MenuCardPanel mcp = (MenuCardPanel)menuCardPanelList.get(i);
                if (mb.getTargetPanel().getName().equals(mcp.getName())) {
                    mb.doClick();
                    break;
                }
            }
        }


        List<MenuItemJButton> menuItemJButtonList = mv.currentCard.getCardMenuItems();

        if (quantity > 1) {
            String quantityy = Integer.toString(quantity);
            for (char c : quantityy.toCharArray()) {
                KeypadPanelJButton keypadPanelJButton = (KeypadPanelJButton) mv.currentCard.getKeypadPanel().getComponent(quantity-1);
                keypadPanelJButton.doClick();
            }
        }

        for (MenuItemJButton mij: menuItemJButtonList) {
            if (mij.getText().equals(buttonName)) {
                mij.doClick();
            }
        }

    }

    @Test
    public void rememberTabTest() {
        // GIVEN: the user is on the select table menu and all of the tables a currently free
        // WHEN: the user chooses to open table 8
        selectTableView.getTableButtons()[8].doClick();
        // THEN: a message appears saying: Would you like to open Table 8
        assertThat(selectTableView.getOutputLabel().getText(), CoreMatchers.containsString("Would you like to open Table 8"));
        // WHEN: the table is opened
        openTable();
        MenuView menuView = waiterClientController.getSelectTableController().getMenuController().getView();
        assertThat(menuView.isVisible(), CoreMatchers.is(true));
        // AND: 2  beef burgers are selected
        selectMenuItem("Beef Burger", 2);
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("Beef Burger"));
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("2"));
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("£11.80"));
        // AND: the order is sent
        getOperationButton("Send Order").doClick();
        // THEN: the status of the table is set to occupied
        assertThat(menuView.isVisible(), CoreMatchers.is(false));
        assertThat(selectTableView.getTableButtons()[8].getText(), CoreMatchers.containsString("Occupied"));
        //WHEN: The table is opened again
        selectTableView.getTableButtons()[8].doClick();
        openTable();
        // THEN: the tab remains up to date
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("Beef Burger"));
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("2"));
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("£11.80"));
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