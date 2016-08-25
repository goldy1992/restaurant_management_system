package com.mike.client.MainMenu.Model;

import com.mike.client.MessageSender;
import com.mike.item.dbItem.ItemDAO;
import com.mike.item.dbItem.MenuPageDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuModel {
	
	@Autowired
	private MessageSender messageSender;
	
    public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	private final String SELECT_MENU_PAGES_QUERY =  "FROM com.mike.item.dbItem.MenuPageDAO";
	private List<MenuPage> menuPages;

	private boolean seenId;
	private int quantitySelected = -1; // -1 defaults to 1
	
	public MenuModel() {
		this.setSeenId(false);
	}
	
	public void initialise()  {		
		@SuppressWarnings("unused")
		List<MenuPageDAO> results = messageSender.query(SELECT_MENU_PAGES_QUERY);
		this.setMenuPages(buildMenuPages(results));
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
}
