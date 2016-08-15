package com.mike.client.MainMenu.Model;

import com.mike.client.MessageSender;
import com.mike.item.dbItem.MenuPageDAO;
import com.mike.message.Response.databaseResponse.QueryResponse;
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
    
	private List query(String query) {
		QueryResponse response = messageSender.sendDbQuery(query);
		return response.getResultSet();
	}
	private List<MenuPage> menuPages;
	
	public MenuModel() {
	}
	
	public void initialise()  {		
			@SuppressWarnings("unused")
			List<MenuPageDAO> results = query(SELECT_MENU_PAGES_QUERY);
			this.setMenuPages(buildMenuPages(results));
			this.setMenuPages(populateMenuPages(getMenuPages()));	
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
    		menuPagesList.add(menuPage);
    	}

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
                    
    	return menuPagesList;
    }
	
	private static String buildFindButtonsForMenuPanelQuery(String menuPanelName) {
		return 	 "SELECT i FROM ItemDAO i " 
			      + "JOIN i.menuPages p "
			      + "WHERE p.name = " 
			      + "'" + menuPanelName + "'";	
	}

	private List<MenuPage> populateMenuPages(List<MenuPage> menuPageList) {
	    // FIND ALL BUTTONS FOR EACH PANEL
	    for (MenuPage c : menuPageList ) {
	    //	List<ItemDAO> itemsOnMenuPage =  query(buildFindButtonsForMenuPanelQuery(c.getName()));
	    	//c = addButtonsToCard(c, results);	
	    } // for each
	  
	        // CODE TO MOVE MAIN PAGE TO THE FRONT OF THE ARRAYLIST            
	    for (int i = 1; i < menuPageList.size(); i++) {
	        if (menuPageList.get(i).getName().equals("MAIN_PAGE")) {
	            Collections.swap(menuPageList, i, 0);
				break;
	        }
	    }    
		
		return menuPageList;
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
}
