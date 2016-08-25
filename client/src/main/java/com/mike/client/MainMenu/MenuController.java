package com.mike.client.MainMenu;

import com.mike.client.MainMenu.Model.MenuModel;
import com.mike.client.MessageSender;
import com.mike.client.SelectTableMenu.View.SelectTableView;
import com.mike.item.Item;
import com.mike.item.Tab;
import com.mike.item.dbItem.ItemDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuController extends JComponent implements ActionListener {
	 
    public Menu view;
    private MenuModel model;
   
    
    public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	@Autowired
    public MessageSender messageSender;
    
	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	
	public void init(SelectTableView tableView, Tab tab) {
		 // initialise the connection to the database
     
		//this.model = new MenuModel();
		this.model.initialise();
		this.view = new Menu(this, tableView, model, true, tab);
		view.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() instanceof MenuItemJButton) {
			parseMenuItem((MenuItemJButton)(ae.getSource()));
		}
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
		view.newTab.addItem(newItem);

		model.setQuantitySelected(-1);
		view.quantityTextPane.setText("");


		// MyClient.debugGUI.addText( newItem.toString() + "\n");
		// CODE TO ADD SCREEN

		String currentText = view.outputTextPane.getText();
		if (view.messageForLatestItem)
			view.outputTextPane.setText(currentText + "\n" + newItem.outputToScreen());
		else view.outputTextPane.setText(currentText + newItem.outputToScreen());
		view.messageForLatestItem = false;

		// ADD TOTAL
		view.setTotal();
	} // parseMenuItem
} // class