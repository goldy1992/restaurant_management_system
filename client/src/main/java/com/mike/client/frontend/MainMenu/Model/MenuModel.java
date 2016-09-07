package com.mike.client.frontend.MainMenu.Model;

import com.mike.item.Item;
import com.mike.item.Tab;
import com.mike.item.dbItem.ItemDAO;
import com.mike.item.dbItem.MenuPageDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuModel {

	private List<MenuPage> menuPages;
	private Tab oldTab;
	private Tab newTab;
	protected double total;
	private boolean seenId;
	private int quantitySelected = -1; // -1 defaults to 1
	private boolean messageForLatestItem = false;
	public String lastReceipt = null;
	private String currentBill = null;
	
	public MenuModel() {
		this.setSeenId(false);
	}
	
	public void initialise(List<MenuPageDAO> menuPageDAOs, Tab tab)  {
		this.setMenuPages(buildMenuPages(menuPageDAOs));
		this.setUpTab(tab);
	}

	public final void setUpTab(Tab tab) {
		if (tab != null)  {
			// set up current tab
			this.setOldTab(tab);
			this.setNewTab(new Tab());
			// sets the table
			//   this.newTab = new Tab(oldTab.getParent());
		} else {
			//  this.oldTab = new Tab(new Table(0));
			//  this.newTab = new Tab(new Table(0));
		}
	}

	public double getTotal() {
		double total = getOldTab().getTotal();
		total += getNewTab().getTotal();
		return total;
	} // getTotal

	public void resetSeenId(){
		seenId = false;
	}

	/*
	 * Merges all items from the new tab into the old tab
	 */
	public Tab mergeTabs() {
		this.oldTab.mergeTabs(newTab);
		return this.oldTab;
	}

	public List<MenuPage> getMenuPages() {
		return menuPages;
	}

	public void setMenuPages(List<MenuPage> menuPages) {
		this.menuPages = menuPages;
	}
	
	public List<MenuPage> buildMenuPages(List<MenuPageDAO> menuPages) {
		List<MenuPage> menuPagesList = new ArrayList<>();
		menuPagesList.size();

    	for (MenuPageDAO mp : menuPages) {
    		MenuPage menuPage = new MenuPage(mp.getName());
    		menuPage.setButtonName(mp.getButtonName());
			menuPage.setParentPageName(mp.getParentPageId());
			menuPage.setChildMenuPages(new ArrayList<>());
			menuPage.setMenuItems(new ArrayList<>());

			for (ItemDAO i : mp.getItems()) {
				menuPage.getMenuItems().add(new MenuItem(i));
			}

    		menuPagesList.add(menuPage);
    	}

		// SET EVERY CARD'S PARENT
		for (MenuPage mp : menuPagesList) {
			if (mp.getParentPageName() != null) {
				for (MenuPage c : menuPagesList) {
					if (c.getName().equals(mp.getParentPageName())) {
						mp.setParentPage(c);
					}
				}
			}
		}
         // ADD EVERY CARD'S PARENT AND CHILDREN PANELS
		for (MenuPage mp : menuPagesList) {
			for (MenuPage c : menuPagesList) {
				if (c.getName().equals(mp.getParentPageName())) {
					c.getChildMenuPages().add(mp);
				}
			}
		}

		// CODE TO MOVE MAIN PAGE TO THE FRONT OF THE ARRAYLIST
		for (int i = 1; i < menuPagesList.size(); i++) {
			if (menuPagesList.get(i).getName().equals("MAIN_PAGE")) {
				Collections.swap(menuPagesList, i, 0);
				break;
			}
		}

		return menuPagesList;
    }
	
	private static String buildFindButtonsForMenuPanelQuery(String menuPanelName) {
		return 	 "SELECT i FROM ItemDAO i " 
			      + "JOIN i.menuPages p "
			      + "WHERE p.name = " 
			      + "'" + menuPanelName + "'";	
	}

    private static MenuPage addButtonsToCard(MenuPage c, ResultSet results) throws SQLException {
    	
        while(results.next())
        {
            MenuItem newItem = new MenuItem(results.getString(1), 
                    results.getInt(2), results.getDouble(3), 
                    results.getString(4), 
                    results.getBoolean(5), results.getBoolean(6));
            c.getMenuItems().add(newItem);          
        } // while
        return c;
        
    }

	public boolean isSeenId() {
		return seenId;
	}

	public void setSeenId(boolean seenId) {
		this.seenId = seenId;
	}

	public int getQuantitySelected() {
		return quantitySelected;
	}

	public void setQuantitySelected(int quantitySelected) {
		this.quantitySelected = quantitySelected;
	}

	public Tab getOldTab() {
		return oldTab;
	}

	public void setOldTab(Tab oldTab) {
		this.oldTab = oldTab;
	}

	public Tab getNewTab() {
		return newTab;
	}

	public void setNewTab(Tab newTab) {
		this.newTab = newTab;
	}

	public String calculateBill() {
		Tab methodOldTab = null;
		Tab methodNewTab = null;
        try {
            methodOldTab = Tab.cloneTab(getOldTab());
            methodNewTab = Tab.cloneTab(getNewTab());
        } // try
        catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
        } // catch

		if (methodOldTab == null || methodNewTab == null)
			return "";

		methodOldTab.mergeTabs(methodNewTab);
		System.out.println("methodOldtab: " + methodOldTab.getItems());

		HashMap<String, Item> billFormat = new HashMap();

		for (Item i : methodOldTab.getItems()) {
			if (billFormat.containsKey(i.getName())) {
				Item item = billFormat.get(i.getName());
				item.setQuantity(item.getQuantity() + i.getQuantity());
			}  // if
			else
				billFormat.put(i.getName(), new Item(i));
		} // for

		String bill = "";
		double total = 0;
		for (Item item : billFormat.values()) {
			total += item.getTotalPrice();
			bill += item.firstLineScreenOutput();
		}

		DecimalFormat df = new DecimalFormat("0.00");
		bill += "\nTotal: £" + df.format(total) + "\n";
		return bill;
	} // calculateBill

	public void writeBill() throws IOException {

		File file = new File("Bill_Table_"
				//+ oldTab.getTable().getTableNumber()
				+ ".txt");
		int i = 1;
		while (file.exists()) {
			file = new File("Bill_Table_" +
					//oldTab.getTable().getTableNumber()
					"(" + i + ")" + ".txt");
			i++;
		}

		// creates the file
		file.createNewFile();
		// Writes the content to the file
		try (FileWriter writer = new FileWriter(file)) {
			// Writes the content to the file
			writer.write(this.getCurrentBill());
			writer.flush();

		} catch (IOException ex) {
			Logger.getLogger(MenuModel.class.getName()).log(Level.SEVERE, null, ex);
		}
	} // writeBill

	public void addDigitToQuantity(int number) {
		if (number < 0) {
			quantitySelected = -1;
		} else {
			quantitySelected = (quantitySelected <= 0) ? number : (quantitySelected * 10) + number;
		}
	} // addDigitToQuantity

	public String getCurrentBill() { return calculateBill(); }
	public boolean isMessageForLatestItem() { return messageForLatestItem; }
	public void setMessageForLatestItem(boolean messageForLatestItem) { this.messageForLatestItem = messageForLatestItem; }
}
