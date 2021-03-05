package com.github.goldy1992.rms.client;

import com.github.goldy1992.rms.client.frontend.MainMenu.View.MenuView;
import com.github.goldy1992.rms.client.frontend.SelectTableMenu.View.SelectTableView;
import com.github.goldy1992.rms.client.frontend.till.TillClientController;
import com.github.goldy1992.rms.client.frontend.till.tillMenu.TillMenuView;
import com.github.goldy1992.rms.client.frontend.waiter.WaiterClientController;
import com.github.goldy1992.rms.client.gui.MenuViewPage;
import com.github.goldy1992.rms.client.gui.SelectTabPage;
import com.github.goldy1992.rms.client.gui.SelectTableViewPage;
import org.apache.log4j.Logger;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.SocketUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class WaiterClientEndToEndTest {

    final static Logger logger = Logger.getLogger(WaiterClientEndToEndTest.class);

    private enum ClientType {
        WAITER,
        TILL_CLIENT
    };

    private ProcessBuilder pb;
    private Process p;
    File outputFile;
    File errorFile;
    SelectTableView selectTableView;
    WaiterClientController waiterClientController;
    TillClientController tillClientController;
    GenericXmlApplicationContext waiterClientcontext;
    GenericXmlApplicationContext tillClientContext;
    OutputStream os;

    @Before
    public void setup() throws IOException, ClassNotFoundException, InterruptedException {
        deployServer();
        deployWaiterClient();
        deployTillClient();
    }

    private void deployServer() throws IOException {
        SecureRandom random = new SecureRandom();
        String randomString = new BigInteger(130, random).toString(32);
        outputFile = new File("output");
        errorFile = new File("error");
        pb = new ProcessBuilder();
        pb.directory(new File(System.getProperty("user.dir")));
        pb.command(new String[]{"cmd.exe", "/c", "cd", "..", "&", "mvn", "clean", "install", "-DskipTests=true", "&", "cd", "server", "&", "mvn", "exec:java", "-Dexec.mainClass=\"com.github.goldy1992.rms.server.StartServer\"", "-Dexec.args=\"" +  randomString + "\""});
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

    private void deployWaiterClient() {
        waiterClientcontext = setupContext(ClientType.WAITER);
        waiterClientController = (WaiterClientController) waiterClientcontext.getBean("waiterClientController");
        waiterClientController.init();
        selectTableView = waiterClientController.getSelectTableController().view;
    }

    private void deployTillClient() {
        tillClientContext = setupContext(ClientType.TILL_CLIENT);
        tillClientController
                = (TillClientController) tillClientContext.getBean("tillClientController");
        tillClientController.init();
    }

    @After
    public void tearDown() throws IOException {
        String exit = "exit";
        os.write(exit.getBytes());
        os.flush();
        os.close();
        outputFile.delete();
        errorFile.delete();
        p.destroy();
        logger.info("p alive: " + p.isAlive());
        waiterClientcontext.destroy();
        tillClientContext.destroy();
    }

    private void openTillClientMenu(TillClientController methodTillClientController) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                methodTillClientController.getView().getStartClientButton().doClick();
            }
        });
        t.start();

        Pause.pause(new Condition("waiting for view to appear") {
            @Override
            public boolean test() {
                if (methodTillClientController.getTillMenuController().getView() == null) {
                    return false;
                }

                if (!methodTillClientController.getTillMenuController().getView().isVisible()) {
                    return false;
                }
                return true;
            }
        });
    }

    @Test
    public void createAndPayOffTabTest() {
        // GIVEN: the user is on the select table menu and all of the tables a currently free
        // WHEN: the user chooses to open table 8
        SelectTableViewPage selectTableViewPage = new SelectTableViewPage(selectTableView);
        selectTableViewPage.clickTable(8);
        // THEN: a message appears saying: Would you like to open Table 8
        assertThat(selectTableView.getOutputLabel().getText(), CoreMatchers.containsString("Would you like to open Table 8"));
        // WHEN: the table is opened
        selectTableViewPage.openTable(waiterClientController.getSelectTableController().getMenuController());
        MenuView menuView = waiterClientController.getSelectTableController().getMenuController().getView();
        assertThat(menuView.isVisible(), CoreMatchers.is(true));
        MenuViewPage menuViewPage = new MenuViewPage(menuView);
        // AND: 2  beef burgers are selected
        menuViewPage.selectMenuItem("Beef Burger", 2);
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("Beef Burger"));
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("2"));
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("£11.80"));
        // AND: the order is sent
        menuViewPage.getOperationButton("Send Order").doClick();
        // THEN: the status of the table is set to occupied
        assertThat(menuView.isVisible(), CoreMatchers.is(false));

        // assert commented out and replaced with pause and wait condition due to flakiness
        // assertThat(selectTableView.getTableButtons()[8].getText(), CoreMatchers.containsString("Occupied"));
        Pause.pause(new Condition("waiting for table status to cahnge") {
            @Override
            public boolean test() {
                return selectTableView.getTableButtons()[8].getText().contains("Occupied");
            }
        });

        //WHEN: The table is opened again
        selectTableView.getTableButtons()[8].doClick();
        selectTableViewPage.openTable(waiterClientController.getSelectTableController().getMenuController());
        // THEN: the tab remains up to date
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("Beef Burger"));
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("2"));
        assertThat(menuView.getOutputTextPane().getText(), CoreMatchers.containsString("£11.80"));
        menuViewPage.getOperationButton("Send Order").doClick();


        // GIVEN: a till client
        openTillClientMenu(tillClientController);

        // WHEN: table tab 8 is loaded
        TillMenuView menuViewTillClient = (TillMenuView)tillClientController.getTillMenuController().getView();
        menuViewTillClient.getMenuItemButtons();
        MenuViewPage tillMenuViewPage = new MenuViewPage(menuViewTillClient);
        SelectTabPage selectTabPage = new SelectTabPage(tillClientController.getTillMenuController().getBarTabMenuController(), tillMenuViewPage.getOperationButton("Bar Tab"));
        selectTabPage.getTable(8).doClick();

        Pause.pause(new Condition("waiting for tab to load") {
            @Override
            public boolean test() {
                return !menuViewTillClient.getOutputTextPane().getText().equals("");
            }
        });

        // THEN: the items added to the menu by the waiter client appear on the output pane
        assertThat(menuViewTillClient.getOutputTextPane().getText(), CoreMatchers.containsString("Beef Burger"));
        assertThat(menuViewTillClient.getOutputTextPane().getText(), CoreMatchers.containsString("2"));
        assertThat(menuViewTillClient.getOutputTextPane().getText(), CoreMatchers.containsString("£11.80"));

        // TODO: add cash pay test functionality
        menuViewPage.inputNumberOnMenuKeypad("2000");
    }

    private GenericXmlApplicationContext setupContext(ClientType type) {
        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

        logger.info("Detect open server socket...");
        int availableServerSocket = SocketUtils.findAvailableTcpPort();

        final Map<String, Object> sockets = new HashMap<String, Object>();
        sockets.put("availableServerSocket", availableServerSocket);

        final MapPropertySource propertySource = new MapPropertySource("sockets", sockets);

        context.getEnvironment().getPropertySources().addLast(propertySource);

        logger.info("using port " + context.getEnvironment().getProperty("availableServerSocket"));

        if (type == ClientType.WAITER) {
            context.load("/META-INF/waiter-client-context.xml");
        } else {
            context.load("/META-INF/till-client-context.xml");
        }
        context.registerShutdownHook();
        context.refresh();

        return context;
    }

}