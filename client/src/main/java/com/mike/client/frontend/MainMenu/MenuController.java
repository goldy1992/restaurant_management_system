package com.mike.client.frontend.MainMenu;

import com.mike.client.backend.MessageSender;
import com.mike.client.frontend.MainMenu.Model.MenuModel;
import com.mike.client.frontend.MainMenu.View.*;
import com.mike.client.frontend.Pair;
import com.mike.item.Item;
import com.mike.item.Tab;
import com.mike.item.dbItem.ItemDAO;
import com.mike.item.dbItem.MenuPageDAO;
import com.mike.message.Table;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

public class MenuController extends JComponent implements ActionListener, MouseListener {

	final static Logger logger = Logger.getLogger(MenuController.class);
	 
    protected MenuView view;
    protected MenuModel model;

    public MenuModel getModel() { return model; }
	public void setModel(MenuModel model) { this.model = model; }

	@Autowired
    public MessageSender messageSender;
	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	
	public <V extends JFrame> void init(V tableView, Tab tab) {
		 // init the connection to the database
		this.model = new MenuModel();
		List<MenuPageDAO> results = getMenuPageDAOs();
		this.model.init(results, tab);
		this.view = new MenuView(this, tableView, model, true, tab);
		view.setVisible(true);

	}

	public List<MenuPageDAO> getMenuPageDAOs() {
		final String SELECT_MENU_PAGES_QUERY =  "FROM com.mike.item.dbItem.MenuPageDAO";
		return messageSender.query(SELECT_MENU_PAGES_QUERY);
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
		getView().addNumberToQuantity(model.getQuantitySelected());
	}

	private void parseKeyJButton(KeyJButton keyJButton) {
		if (model.getNewTab().getNumberOfItems() > 0) {
			String currentText = getView().getOutputArea().getText();
			currentText += keyJButton.getKey();
			getView().getOutputArea().setText(currentText);
			Tab newT = model.getNewTab();
			newT.getItems().get(newT.getItems().size() - 1).appendCharacter(keyJButton.getKey());
			model.setMessageForLatestItem(true);
		} // if
	}

	private void parseMenuItem(MenuItemJButton menuItemJButton) {
		if (menuItemJButton.isNeedAgeCheck() && !model.isSeenId()) {
			int number = JOptionPane.showConfirmDialog(
					getView(), "Does the customer pass an ID Check?",
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
			//init the connection to the database

			List<ItemDAO> response = messageSender.query("FROM ItemDAO i where i.id = " + menuItemJButton.getId());
			if (response.size() != 1) {
				logger.info("incorrect number of items response");
			}

			int amountInStock = response.get(0).getQuantity();

			if (quantity > amountInStock) {
				JOptionPane.showMessageDialog(getView(), "Error: There's only " + amountInStock + " " + menuItemJButton.getText() + " available in stock");
				model.setQuantitySelected(-1);
				getView().getQuantityTextPane().setText("");
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
		getView().getQuantityTextPane().setText("");


		// MyClient.debugGUI.addText( newItem.toString() + "\n");
		// CODE TO ADD SCREEN

		String currentText = getView().getOutputTextPane().getText();
		if (model.isMessageForLatestItem()) {
			getView().getOutputTextPane().setText(currentText + "\n" + newItem.outputToScreen());
		} else {
			getView().getOutputTextPane().setText(currentText + newItem.outputToScreen());
			model.setMessageForLatestItem(false);
		}
		// ADD TOTAL
		getView().setTotal(model.getTotal());
	} // parseMenuItem

	protected void dealWithButtons(JButton button) {
		switch(button.getText())
		{
			case "Send Order":  sendOrder();  getView().dispose(); break;
			case "Print Bill":  printBill(); break;
			case "Void": voidItem(); break;
			case "Void Last Item": voidLastItem(); break;
			default: break;
		} // switch
	} // dealWithTillMenuButtons()

	protected void sendOrder() {
		logger.info("called send order");
		Tab newItems = model.getNewTab();
		Tab updatedTab = model.mergeTabs();
		model.resetSeenId();
		messageSender.sendTabUpdateNotification(updatedTab, newItems);
		messageSender.sendTableStatusEventNotification(model.getOldTab().getTable(), Table.TableStatus.OCCUPIED);
	} // sendOrder

	protected void printBill() {
		model.calculateBill();
		try {
			model.writeBill();

	//		if (this instanceof TillMenu)
			{
				this.setVisible(false);
			} // if
		//	else
			{
				getView().dispose();
			}
		} // try
		catch (IOException ex) {
			logger.error(ex.getStackTrace().toString());
		} // catch
	} // printBill

	private void voidItem()
	{
		VoidItemsDialog vItem = new VoidItemsDialog(getView(), true, new Pair<>(model.getOldTab(), model.getNewTab()));

		Pair<Tab, Tab> result = vItem.startDialog();
		model.setOldTab(result.getFirst());
		model.setNewTab(result.getSecond());

		getView().setTotal(model.getTotal());
		getView().getOutputTextPane().setText(model.getOldTab().toString() + model.getNewTab().toString());
	}

	private void voidLastItem()
	{
		if (model.getNewTab().getItems().isEmpty()) {
			return;
		}
		model.getNewTab().removeItem(model.getNewTab().getItems().get(model.getNewTab().getItems().size() - 1));
		getView().getOutputTextPane().setText(model.getOldTab().toString() + model.getNewTab().toString());
		getView().setTotal(model.getTotal());
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == this || me.getSource() == getView().getOutputTextPane() || me.getSource() instanceof MenuCardPanel
				|| me.getSource() instanceof KeypadJPanel) {
			getView().switchToParentCard();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {	}

	public MenuView getView() {
		return view;
	}
} // class