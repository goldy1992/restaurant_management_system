package com.mike.client.MainMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JDialog;

import com.mike.client.MainMenu.Model.MenuItem;
import com.mike.client.MainMenu.Model.MenuModel;
import com.mike.client.MainMenu.Model.MenuPage;

public class MenuViewBuilderHelper {

	public static ArrayList<MenuCardPanel> createCardPanelsForView(Menu parent, MenuModel menuModel) {
		
		try {
			ArrayList<MenuCardPanel> cardPanelsList =  buildMenuCardStructure(menuModel, parent);
            // FIND ALL BUTTONS FOR EACH PANEL
            for (MenuCardPanel c : cardPanelsList ) {
            	c = addButtonsToCard(c);
            } // for each
  
            // CODE TO MOVE MAIN PAGE TO THE FRONT OF THE ARRAYLIST            
            for (int i = 1; i < cardPanelsList.size(); i++) {
                if (cardPanelsList.get(i).getName().equals("MAIN_PAGE")) {
                    Collections.swap(cardPanelsList, i, 0);
                }
            }    
	        return cardPanelsList;      
		} catch (SQLException e) {
			return null;
		}
	}
	
    
    private static ArrayList<MenuCardPanel> buildMenuCardStructure(MenuModel model, Menu parent) throws SQLException {
        ArrayList<MenuCardPanel> cardPanelsList = new ArrayList<>();

        // MAKE AN OBJECT FOR EVERY VIEW CARD PANEL
    	for (MenuPage mp : model.getMenuPages()) {
            MenuCardPanel panel = MenuCardPanel.createMenuCardPanel(parent, mp);    
            panel.setName(mp.getName());
            cardPanelsList.add(panel);
            
            // ADD the kitchen message card option to EVERY card's children
            panel.addChildCardButton(MenuCardLinkJButton.createMenuCardLinkButton(parent.kitchenBarMsgPanel, "Kitchen/Bar Message", parent));
    	}
 
        // ADD EVERY CARD'S PARENT AND CHILDREN PANELS
    	for(MenuCardPanel menuCardPanel : cardPanelsList) {
            MenuCardPanel parentPanel = null;
            
            // for loop to find parent panel
            for (MenuCardPanel c : cardPanelsList ) {
                if (c.getName().equals(menuCardPanel.getName())) {// 2 is column index of PARENT_PAGE_ID
                        parentPanel = c;
                }
            }
            
            // set currentPanels Parent {COULD BE NULL}
            menuCardPanel.setParentPanel(parentPanel);  
            
            // if not null take the parent and create a child button and reference it to panel
            if (parentPanel != null) {
                parentPanel.addChildCardButton(MenuCardLinkJButton.createMenuCardLinkButton(menuCardPanel, menuCardPanel.getName(), parent));
            }
    	}
    	                         
     
            
            // adds all c
            for (MenuCardPanel c1 : cardPanelsList)
                c1.addAllChildCardButtonsToPanel(); 	
            
            return cardPanelsList;
    }
    
    private static MenuCardPanel addButtonsToCard(MenuCardPanel c) throws SQLException {
    	
    	for (MenuItem menuItem : c.getMenuPage().getMenuItems()) {
            MenuItemJButton newButton = new MenuItemJButton (menuItem.getName(), 
                    menuItem.getId(), menuItem.getPrice(), 
                    menuItem.getType(), c, 
                    menuItem.isNeedAgeCheck(), menuItem.isStockCount());
            c.addMenuItemButton(newButton);          
        } // while
        
        c.addAllItemsToPanel();
        return c;
    }
 

}
