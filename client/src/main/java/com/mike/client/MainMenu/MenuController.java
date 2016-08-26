package com.mike.client.MainMenu;

import com.mike.client.MainMenu.Model.MenuModel;
import com.mike.client.MessageSender;
import com.mike.client.Pair;
import com.mike.client.SelectTableMenu.View.SelectTableView;
import com.mike.item.Item;
import com.mike.item.Tab;
import com.mike.item.dbItem.ItemDAO;
import com.mike.item.dbItem.MenuPageDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController extends JComponent implements ActionListener {
	 
    public Menu view;
    private MenuModel model;

    public MenuModel getModel() { return model; }
	public void setModel(MenuModel model) { this.model = model; }

	@Autowired
    public MessageSender messageSender;
	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	
	public void init(SelectTableView tableView, Tab tab) {
		 // initialise the connection to the database
		this.model = new MenuModel();
		final String SELECT_MENU_PAGES_QUERY =  "FROM com.mike.item.dbItem.MenuPageDAO";
		List<MenuPageDAO> results = messageSender.query(SELECT_MENU_PAGES_QUERY);
		this.model.initialise(results, tab);
		this.view = new Menu(this, tableView, model, true, tab);
		view.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() instanceof MenuItemJButton) {
			parseMenuItem((MenuItemJButton) (ae.getSource()));
		} else if (ae.getSource() instanceof KeyJButton) {
			parseKeyJButton((KeyJButton)(ae.getSource()));
		} else if (ae.getSource() instanceof KeypadPanelJButton) {
			parseKeyPanelJButton((KeypadPanelJButton)(ae.getSource()));
		} else if (ae.getSource() instanceof JButton) {
			dealWithButtons((JButton) ae.getSource());
		}
	}

	private void parseKeyPanelJButton(KeypadPanelJButton keypadPanelJButton) {
		model.addDigitToQuantity(keypadPanelJButton.getNumber());
		view.addNumberToQuantity(model.getQuantitySelected());
	}
	private void parseKeyJButton(KeyJButton keyJButton) {
		if (model.getNewTab().getNumberOfItems() > 0) {
			String currentText = view.getOutputArea().getText();
			currentText += keyJButton.getKey();
			view.getOutputArea().setText(currentText);
			Tab newT = model.getNewTab();
			newT.getItems().get(newT.getItems().size() - 1).appendCharacter(keyJButton.getKey());
			model.setMessageForLatestItem(true);
		} // if
	}

	private void parseMenuItem(MenuItemJButton menuItemJButton) {
		if (menuItemJButton.isNeedAgeCheck() && !model.isSeenId()) {
			int number = JOptionPane.showConfirmDialog(
					view, "Does the customer pass an ID Check?",
					"ID Check", JOptionPane.YES_NO_OPTION);

			if (number == 0) {
				model.setSeenId(true);
			} else {
				return;
			}
		} // if

		// parse the quantity
		int quantity = model.getQuantitySelected();
		if (quantity < 0)
			quantity = 1;

		if (menuItemJButton.isStockCount()) {
			//initialise the connection to the database

			List<ItemDAO> response = messageSender.query("FROM ItemDAO i where i.id = " + menuItemJButton.getId());
			if (response.size() != 1) {
				System.out.println("incorrect number of items response");
			}

			int amountInStock = response.get(0).getQuantity();

			if (quantity > amountInStock) {
				JOptionPane.showMessageDialog(view, "Error: There's only " + amountInStock + " " + menuItemJButton.getText() + " available in stock");
				model.setQuantitySelected(-1);
				view.quantityTextPane.setText("");
				return;
			} // if
			else  { // remove from stock
				// query: UPDATE `3YP_ITEMS` SET `QUANTITY` = `QUANTITY` - 1 WHERE ID = 27
				ItemDAO itemDAO = response.get(0);
				itemDAO.setQuantity(itemDAO.getQuantity() - quantity);
				messageSender.update(itemDAO);
			} // else
		} // if

		// CODE TO ADD TO TAB SHOULD BE PUT HERE
		Item newItem = new Item(menuItemJButton.getId(), menuItemJButton.getText(), menuItemJButton.getPrice(), menuItemJButton.getType(), quantity, menuItemJButton.isStockCount());
		model.getNewTab().addItem(newItem);

		model.setQuantitySelected(-1);
		view.quantityTextPane.setText("");


		// MyClient.debugGUI.addText( newItem.toString() + "\n");
		// CODE TO ADD SCREEN

		String currentText = view.getOutputTextPane().getText();
		if (model.isMessageForLatestItem()) {
			view.getOutputTextPane().setText(currentText + "\n" + newItem.outputToScreen());
		} else {
			view.getOutputTextPane().setText(currentText + newItem.outputToScreen());
			model.setMessageForLatestItem(false);
		}
		// ADD TOTAL
		view.setTotal(model.getTotal());
	} // parseMenuItem

	private void dealWithButtons(JButton button)
	{
		switch(button.getText())
		{
			case "Send Order":  sendOrder();  view.dispose(); break;
			case "Print Bill":  printBill(); break;
			case "Void": voidItem(); break;
			case "Void Last Item": voidLastItem(); break;
			default: break;
		} // switch
	} // dealWithButtons()

	private void sendOrder() {
		System.out.println("called send order");
		model.mergeTabs();
		model.resetSeenId();

	//	if (!(this instanceof TillMenu))
	 	{
//			TableStatusEvtNfn newEvt1;
//                newEvt1 = new TableStatusEvtNfn(InetAddress.getByName(parentClient.client.getLocalAddress().getHostName()),
//                InetAddress.getByName(parentClient.serverAddress.getHostName()),
//                oldTab.getTable().getTableNumber(), Table.TableStatus.OCCUPIED);
//                out.reset();
//                out.writeObject(newEvt1);
		}

		//   this.newTab = new Tab(oldTab.getTable());

	} // sendOrder

	public void printBill()
	{
		model.calculateBill();
		try {
			model.writeBill();

	//		if (this instanceof TillMenu)
			{
				this.setVisible(false);
			} // if
		//	else
			{
				view.dispose();
			}
		} // try
		catch (IOException ex) {
			Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
		} // catch
	} // printBill

	private void voidItem()
	{
		VoidItemsDialog vItem = new VoidItemsDialog(view, true, new Pair<>(model.getOldTab(), model.getNewTab()));

		Pair<Tab, Tab> result = vItem.startDialog();
		model.setOldTab(result.getFirst());
		model.setNewTab(result.getSecond());

		view.setTotal(model.getTotal());
		view.getOutputTextPane().setText(model.getOldTab().toString() + model.getNewTab().toString());
	}

	private void voidLastItem()
	{
		if (model.getNewTab().getItems().isEmpty()) {
			return;
		}
		model.getNewTab().removeItem(model.getNewTab().getItems().get(model.getNewTab().getItems().size() - 1));
		view.getOutputTextPane().setText(model.getOldTab().toString() + model.getNewTab().toString());
		view.setTotal(model.getTotal());
	}

} // class