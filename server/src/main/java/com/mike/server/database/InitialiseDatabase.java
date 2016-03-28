package com.mike.server.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mike.server.database.tables.FOOD_OR_DRINK;
import com.mike.server.database.tables.ITEMS;
import com.mike.server.database.tables.MENU_PAGE;
import com.mike.server.database.tables.POS_IN_MENU;

public class InitialiseDatabase {
	
	@Autowired
	public DatabaseConnector dbCon;
	
	public DatabaseConnector getDbCon() {
		return dbCon;
	}

	public void setDbCon(DatabaseConnector dbCon) {
		this.dbCon = dbCon;
	}

	public InitialiseDatabase() {;
	}
	
	public void init() {
		insertItem("14oz Cola", 1.5f, 100, false, false, FOOD_OR_DRINK.FOOD);
		insertItem("14oz Diet Cola", 1.5000f, 100, false, false, FOOD_OR_DRINK.DRINK);
		insertItem("4oz Dash Cola", 0.3f, 1000, false, false, FOOD_OR_DRINK.DRINK);
		insertItem("4oz Dash Diet Cola", 0.3f, 1000, false, false, FOOD_OR_DRINK.DRINK);
		insertItem("Pint Cola", 2.0f, 100, false, false, FOOD_OR_DRINK.DRINK);
		insertItem("Pint Diet Cola", 2.0f, 100, false, false, FOOD_OR_DRINK.DRINK);
		insertItem("Cheese Pie", 6.95f, 5509, true, false, FOOD_OR_DRINK.FOOD);
		insertItem("Beef Burger", 5.9f, 100, true, false, FOOD_OR_DRINK.FOOD);
		insertItem("Beef Lasagne", 8.95f, 150, false, false, FOOD_OR_DRINK.FOOD);
		
		insertMenuPage("BURGER_PAGE", "FOOD_PAGE", "Burgers");
		insertMenuPage("FOOD_PAGE", "MAIN_PAGE", "Food Menu");
		insertMenuPage("GRILLS_PAGE", "FOOD_PAGE", "Grills");
		insertMenuPage("MAIN_PAGE", null, "Main Page");
		insertMenuPage("MIN_MIX_PAGE", "MAIN_PAGE", "Minerals/Mixers");
		insertMenuPage("TRAGO_PAGE", "MAIN_PAGE", "Spirits/Liqueurs");

		String query="FROM ITEMS I WHERE I.id = 3";
		List<ITEMS> res = dbCon.query(query);
		ITEMS i = res.get(0);
		System.out.println(i.getId() + "\nname: " + i.getName());
		insertPosInMenu(i.getId(), "MAIN_PAGE");
//		insertPosInMenu(3L, "MIN_MIX_PAGE");
//		insertPosInMenu(4L, "MIN_MIX_PAGE");
//		insertPosInMenu(5L, "MIN_MIX_PAGE");
//		insertPosInMenu(6L, "MIN_MIX_PAGE");
//		insertPosInMenu(7L, "MIN_MIX_PAGE");
//		insertPosInMenu(8L, "MIN_MIX_PAGE");
//		insertPosInMenu(19L, "FOOD_PAGE");
//		insertPosInMenu(27L, "BURGER_PAGE");
//		insertPosInMenu(29L, "FOOD_PAGE");
	}
	
	private void insertItem(String name, float price, int quantity, boolean stockCountOn, boolean needsAgeCheck, FOOD_OR_DRINK foodOrDrink) {
		ITEMS items = ITEMS.createItem(name, price, quantity, stockCountOn, needsAgeCheck, foodOrDrink);
		dbCon.insert(items);		
	}
	
	private void insertMenuPage(String name, String parentPageId, String buttonName) {
		MENU_PAGE menuPage = MENU_PAGE.createMenuPage(name, parentPageId, buttonName);
		dbCon.insert(menuPage);
	}
	
	private void insertPosInMenu(Long id, String location) {
		POS_IN_MENU posInMenu = POS_IN_MENU.createMenuPage(id, location);
		dbCon.insert(posInMenu);
	}

}
