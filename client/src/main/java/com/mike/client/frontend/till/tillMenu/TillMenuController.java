package com.mike.client.frontend.till.tillMenu;

import com.mike.client.frontend.MainMenu.MenuController;
import com.mike.client.frontend.till.TillClientController;
import com.mike.client.frontend.till.tillMenu.barTabMenu.BarTabMenuController;
import com.mike.client.frontend.till.tillMenu.barTabMenu.BarTabMenuModel;
import com.mike.item.Tab;
import com.mike.item.dbItem.MenuPageDAO;
import com.mike.message.Response.TabResponse;
import com.mike.message.Table;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by michaelg on 07/09/2016.
 */
public class TillMenuController extends MenuController {

	@Autowired
	private BarTabMenuController barTabMenuController;

	private TillClientController tillClientController;
	public TillMenuController() {
		super();
	}


	public <V extends JFrame> void initTillMenu(TillClientController tillClientController, V tillView, Tab tab) {
		this.tillClientController = tillClientController;
		List<MenuPageDAO> menuPageDAOs = getMenuPageDAOs();
		this.model = new TillMenuModel(tab);
		model.init(menuPageDAOs, tab);
		this.view = new TillMenuView(this, tillView, model, true, tab);
		this.getView().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (!dealWithTillMenuButtons((JButton)ae.getSource())) {
			super.actionPerformed(ae);
		}
	}

	private void barTabPressed() {
		TillMenuModel model = (TillMenuModel)this.model;
		TillMenuView view = (TillMenuView) this.getView();
		Integer chosenTabNumber = null;
		if (!model.isTabLoaded()) {
			System.out.println("tabLoaded == false");
			if (model.getOldTab().getNumberOfItems() + model.getNewTab().getNumberOfItems() <= 0) {
				System.out.println("get tab selected");
				chosenTabNumber = getBarTabMenuController().getTabNumber(view, true, BarTabMenuModel.Functionality.GET_TAB);

				if (chosenTabNumber == null) {
					view.getQuantityTextPane().setText("there are no tabs to display!");
					return;
				} else {

					messageSender.sendTableStatusEventNotification(chosenTabNumber, Table.TableStatus.IN_USE);
					TabResponse response = messageSender.sendTabRequest(chosenTabNumber);
					this.model.setOldTab(response.getTab());
					this.model.setNewTab(new Tab());
					this.getView().setUpTab(response.getTab());
				}
			} else {
				chosenTabNumber = getBarTabMenuController().getTabNumber(view, true, BarTabMenuModel.Functionality.ADD_TO_TAB);
				messageSender.sendTableStatusEventNotification(chosenTabNumber, Table.TableStatus.IN_USE);
				TabResponse response = messageSender.sendTabRequest(chosenTabNumber);
				Tab updatedTab = response.getTab().mergeTabs(model.getNewTab());
				updatedTab.setTabNumber(chosenTabNumber);
				// TODO: send the orde after working on kitchen and bar client
			}
			model.setTabLoaded(true);
		} else {
			model.getOldTab().mergeTabs(model.getNewTab());
			sendOrder();
			view.setUpTab(null);
			view.getOutputTextPane().setText("");
			model.setTabLoaded(false);
			view.dispose();
		}
	}

	public void writeLastReceipt() {
//		System.out.println("called last receipt");
//		if (this.lastReceipt == null)
//			return;
//		try
//		{
//			System.out.println("last receipt not null");
//			File file = new File("Last _Bill_Receipt_"
//					//+ oldTab.getTable().getTableNumber()
//					+ ".txt");
//			int i = 1;
//			while (file.exists())
//			{
//				file = new File("Last _Bill_Receipt_" //+ oldTab.getTable().getTableNumber()
//						+ "(" + i + ")" + ".txt");
//				i++;
//			} // while
//
//			// creates the file
//			file.createNewFile();
//			// Writes the content to the file
//			try ( FileWriter writer = new FileWriter(file) )
//			{
//				// Writes the content to the file
//				writer.write(this.lastReceipt);
//				writer.flush();
//			}
//			sendOrder();
//
//			this.dispose();
//
//		}
//		catch (IOException ex) {
//			Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);}
	} // writeBill

	@Override
	protected void sendOrder() {
		Tab updatedTab = model.mergeTabs();
		messageSender.sendTabUpdateNotification(updatedTab, model.getNewTab());
		tillClientController.updateTab(updatedTab);
		getView().dispose();
	}

	protected void cashPay() {
		Float amount = null;
		Float change = 0f;
		try {
			amount = Float.parseFloat(getView().getQuantityTextPane().getText());
			amount = amount * 0.01f;
		} catch (Exception e) {
			return;
		}
		if ((float)amount >= model.getTotal()) {
			tillClientController.setChange(amount - (float)model.getTotal());
			if (model.getOldTab().getTable() == 0) {
				Tab updatedTab = model.mergeTabs();
				messageSender.sendTabUpdateNotification(updatedTab, model.getNewTab());
				tillClientController.setPreviousTab(updatedTab);
				tillClientController.updateTab(new Tab());
			} else {
				messageSender.sendTableStatusEventNotification(model.getOldTab().getTable(), Table.TableStatus.DIRTY);
			}
		} else {
			getView().getQuantityTextPane().setText("");
		}
		getView().dispose();
	}

	public boolean dealWithTillMenuButtons(JButton button) {
		switch (button.getText()) {
			case "Bar Tab": barTabPressed(); return true;
			case "Print Last Receipt": writeLastReceipt(); return true;
			case "Send Order": sendOrder(); return true;
			case "Cash Pay": cashPay(); return true;
			default: break;
		} // switch
		return false;
	}

	public BarTabMenuController getBarTabMenuController() { return barTabMenuController; }
}
