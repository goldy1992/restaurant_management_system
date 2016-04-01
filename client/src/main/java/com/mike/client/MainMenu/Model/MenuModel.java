package com.mike.client.MainMenu.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mike.client.MessageSender;
import com.mike.item.dbItem.MenuPageDAO;
import com.mike.message.Response.databaseResponse.QueryResponse;

public class MenuModel {
	
	@Autowired
	private MessageSender messageSender;
	
    public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	private final String SELECT_MENU_PAGES_QUERY =  "FROM MENU_PAGE";
    
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
	
	public static List<MenuPage> buildMenuPages(List<MenuPageDAO> menuPages) {
		List<MenuPage> menuPagesList = new ArrayList<>();
		menuPagesList.size();

    	for (MenuPageDAO mp : menuPages) {
    		menuPagesList.add(new MenuPage(mp.getButtonName()));
    	}
                            
//         // ADD EVERY CARD'S PARENT AND CHILDREN PANELS
//        int count = 0; // keeps count to remember current panel
//        results.first(); // uses same resultsSet and goes to the first row
//        do
//        {
//        	MenuPage currentMenuPage = menuPagesList.get(count);
//            MenuPage parentMenuPage = null;
//            
//            // for loop to find parent panel
//            for (MenuPage c : menuPagesList ) {
//                if (c.getName().equals(results.getString(2))) { // 2 is column index of PARENT_PAGE_ID
//                    parentMenuPage = c;
//                    break;
//                }
//            }
//            
//            // set currentPanels Parent {COULD BE NULL}
//            currentMenuPage.setParentPage(parentMenuPage);  
//            
//            // if not null take the parent and create a child button and reference it to panel
//            if (parentMenuPage != null) {
//                parentMenuPage.getChildMenuPages().add(currentMenuPage);
//            }
//            
//            count++;
//        }  while (results.next());
               
        //return menuPagesList;
		return null;
    }
	
	private static String buildFindButtonsForMenuPanelQuery(String menuPanelName) {
		return "SELECT `NAME`, `3YP_ITEMS`.`ID`, `PRICE`, "
                + "`FOOD_OR_DRINK`, `NEED_AGE_CHECK`, `STOCK_COUNT_ON` "
                + "FROM `3YP_ITEMS` " 
                + "LEFT JOIN `3YP_POS_IN_MENU` ON "
                + "`3YP_ITEMS`.`ID` = `3YP_POS_IN_MENU`.`ID` "
                + "WHERE `3YP_POS_IN_MENU`.`LOCATION` = \"" 
                + menuPanelName + "\" ";
	}

	private static List<MenuPage> populateMenuPages(List<MenuPage> menuPageList) {
	
		try {
            // FIND ALL BUTTONS FOR EACH PANEL
            for (MenuPage c : menuPageList ) {
            	ResultSet results = null;//query(buildFindButtonsForMenuPanelQuery(c.getName()));
            	
            	c = addButtonsToCard(c, results);	
            } // for each
	  
            // CODE TO MOVE MAIN PAGE TO THE FRONT OF THE ARRAYLIST            
            for (int i = 1; i < menuPageList.size(); i++) {
                if (menuPageList.get(i).getName().equals("MAIN_PAGE")) {
                    Collections.swap(menuPageList, i, 0);
                }
            }    
		} catch (SQLException e) {
			return null;
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
