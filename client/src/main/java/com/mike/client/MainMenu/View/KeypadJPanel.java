package com.mike.client.MainMenu.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by michaelg on 30/08/2016.
 */
public class KeypadJPanel extends JPanel {

	public KeypadJPanel() {
		super();
	}

	public static KeypadJPanel createKeypadPanel() {
		KeypadJPanel newKeypadPanel = new KeypadJPanel();
		newKeypadPanel.addMouseListener(MenuView.menuController);
		newKeypadPanel.setLayout(new GridLayout(4,3));

		// makes the first 9 buttons
		for (int i= 1; i <= 10; i++)
		{
			final int x; if (i == 10)  x = 0; else x = i; // declared final so can be used in the actionlistener method
			KeypadPanelJButton number = new KeypadPanelJButton(x + "", x);
			number.addActionListener(MenuView.menuController);
			newKeypadPanel.add(number);
		} // for
        /* Make the clear button */
		KeypadPanelJButton clear = new KeypadPanelJButton("Clear", -1);
		clear.addActionListener(MenuView.menuController);

		newKeypadPanel.add(clear);

//		if (belongsToMenu.getClass() == TillMenu.class) {
//			System.out.println("entered");
//			JButton cashPay = new JButton("Cash Pay");
//			cashPay.addActionListener(new ActionListener()
//			{
//
//				public void updateTakings(double amount)
//				{
//					try
//					{
//						Connection con;
//						//initialise the connection to the database
//						con = DriverManager.getConnection(
//								"jdbc:mysql://dbhost.cs.man.ac.uk:3306/mbbx9mg3",
//								"mbbx9mg3",
//								"Fincherz+2013");
//
//						DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
//						Date date = new Date();
//						String fechaDeHoy = df.format(date);
//						System.out.println("date: " + fechaDeHoy);
//						// query: UPDATE `3YP_ITEMS` SET `QUANTITY` = `QUANTITY` - 1 WHERE ID = 27
//						PreparedStatement numberOfButtonsQuery = null;
//
//						String query = "SELECT * FROM `3YP_TAKINGS` "
//								+ "WHERE `3YP_TAKINGS`.`DATE` = \""
//								+ fechaDeHoy + "\"";
//
//						numberOfButtonsQuery = con.prepareStatement(query);
//						numberOfButtonsQuery.executeQuery();
//						ResultSet results = numberOfButtonsQuery.getResultSet();
//
//						boolean gotResult = false;
//
//						while(results.next())
//							gotResult = true;
//
//						if (!gotResult)
//						{
//							query = "INSERT INTO `3YP_TAKINGS` "
//									+ "VALUES (\"" + fechaDeHoy + "\", \"0\")";
//							numberOfButtonsQuery = con.prepareStatement(query);
//							numberOfButtonsQuery.executeUpdate();
//						} // if
//
//						// update amount
//						query = "UPDATE `3YP_TAKINGS` "
//								+ "SET `AMOUNT` = `AMOUNT` + \"" + amount + "\""
//								+ "WHERE `3YP_TAKINGS`.`DATE` = \""
//								+ fechaDeHoy + "\"";
//						numberOfButtonsQuery = con.prepareStatement(query);
//						numberOfButtonsQuery.executeUpdate();
//						con.close();
//					} catch (SQLException ex) {
//						Logger.getLogger(MenuCardPanel.class.getName()).log(Level.SEVERE, null, ex);
//					}
//				}
//
//				@Override
//				public void actionPerformed(ActionEvent e)
//				{
////                    TillMenu menu = (TillMenu)belongsToMenu;
//////                    double amount = menu.quantitySelected / 100.00;
////					double amount = menu.quantitySelected / 100.00;
////                    System.out.println("current total: " + menu.getTotalDouble());
////                    if (amount < menu.getTotalDouble())
////                    {
////                        menu.quantityTextPane.setText("Insufficient amount");
////           //             menu.quantitySelected = 0;
////                    } // if
////                    else
////                    {
////
////                            double change = (amount - menu.getTotalDouble());
////                            DecimalFormat df = new DecimalFormat("0.00");
////                            menu.lastReceipt = menu.calculateBill();
////                            menu.lastReceipt += "Amount given: \t\t£" + df.format(amount) + "\n";
////                            menu.lastReceipt += "Change: \t\t£" + df.format(change);
////                            String changeString = df.format(change);
////                            menu.getTill().getChangeOutputLabel().setText("£" + changeString );
////                            menu.oldTab.mergeTabs(menu.newTab);
////                            boolean x = menu.oldTab.removeAll();
////                            menu.newTab.removeAll();
////                            System.out.println("got here in cash off: " + x);
////
////                            updateTakings(menu.getTotalDouble());
////
////                            if (menu.tabLoaded)
////                            {
////
//////                                TabUpdateNfn newEvt = new TabUpdateNfn(InetAddress.getByName(
//////                                    menu.parentClient.client.getLocalAddress().getHostName()),
//////                                    InetAddress.getByName(menu.parentClient.serverAddress.getHostName()),
//////                                     menu.oldTab);
//////                                menu.out.reset();
//////                                menu.out.writeObject(newEvt);
////
//////                                TableStatusEvtNfn newEvt1 = new TableStatusEvtNfn(InetAddress.getByName(menu.parentClient.client.getLocalAddress().getHostName()),
//////                                    InetAddress.getByName(menu.parentClient.serverAddress.getHostName()),
//////                                    menu.oldTab.getTable().getTableNumber(), Table.TableStatus.DIRTY);
//////
//////                                menu.out.reset();
//////                                menu.out.writeObject(newEvt1);
////                                menu.tabLoaded = false;
////                            }
////                            menu.dispose();
////
////                        menu.outputTextPane.setText("");
////                        menu.setTotal();
////                        menu.quantitySelected = -1;
////                        menu.quantityTextPane.setText("");
////
////                    } // else
//				} // actionPerformed
//			});
//			newKeypadPanel.add(cashPay);
//
//		} // if

		return newKeypadPanel;
	} // createKeypadJPanel
}
