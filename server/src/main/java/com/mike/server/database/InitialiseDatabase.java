package com.mike.server.database;

import com.mike.item.dbItem.FOOD_OR_DRINK;
import com.mike.item.dbItem.ItemDAO;
import com.mike.item.dbItem.MenuPageDAO;
import com.mike.server.MessageRequestRouter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InitialiseDatabase {

	final static Logger logger = Logger.getLogger(InitialiseDatabase.class);
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
		Map<Long, ItemDAO> items = new HashMap<>();
		
		ItemDAO i = ItemDAO.createItem("14oz Cola", 1.5f, 100, true, false, FOOD_OR_DRINK.DRINK);
		items.put(dbCon.insert(i), i);
		
		i = ItemDAO.createItem("14oz Diet Cola", 1.5000f, 100, false, false, FOOD_OR_DRINK.DRINK);
		items.put(dbCon.insert(i), i);
		i = ItemDAO.createItem("4oz Dash Cola", 0.3f, 1000, false, false, FOOD_OR_DRINK.DRINK);
		items.put(dbCon.insert(i), i);
		i = ItemDAO.createItem("4oz Dash Diet Cola", 0.3f, 1000, false, false, FOOD_OR_DRINK.DRINK);
		items.put(dbCon.insert(i), i);
		i = ItemDAO.createItem("Pint Cola", 2.0f, 100, false, false, FOOD_OR_DRINK.DRINK);
		items.put(dbCon.insert(i), i);
		i = ItemDAO.createItem("Pint Diet Cola", 2.0f, 100, false, false, FOOD_OR_DRINK.DRINK);
		items.put(dbCon.insert(i), i);
		i = ItemDAO.createItem("Cheese Pie", 6.95f, 5509, true, false, FOOD_OR_DRINK.FOOD);
		items.put(dbCon.insert(i), i);
		i = ItemDAO.createItem("Beef Burger", 5.9f, 100, true, false, FOOD_OR_DRINK.FOOD);
		items.put(dbCon.insert(i), i);
		i = ItemDAO.createItem("Beef Lasagne", 8.95f, 150, false, false, FOOD_OR_DRINK.FOOD);
		items.put(dbCon.insert(i), i);

		Map<String, MenuPageDAO> menuPages = new HashMap<>();
		MenuPageDAO mp = MenuPageDAO.createMenuPage("BURGER_PAGE", "FOOD_PAGE", "Burgers");
		menuPages.put(dbCon.insert(mp), mp);
		mp = MenuPageDAO.createMenuPage("FOOD_PAGE", "MAIN_PAGE", "Food Menu");
		menuPages.put(dbCon.insert(mp), mp);
		mp = MenuPageDAO.createMenuPage("GRILLS_PAGE", "FOOD_PAGE", "Grills");
		menuPages.put(dbCon.insert(mp), mp);
		mp = MenuPageDAO.createMenuPage("MAIN_PAGE", null, "Main Page");
		menuPages.put(dbCon.insert(mp), mp);
		mp = MenuPageDAO.createMenuPage("MIN_MIX_PAGE", "MAIN_PAGE", "Minerals/Mixers");
		menuPages.put(dbCon.insert(mp), mp);
		mp = MenuPageDAO.createMenuPage("TRAGO_PAGE", "MAIN_PAGE", "Spirits/Liqueurs");
		menuPages.put(dbCon.insert(mp), mp);

		List<ItemDAO> newItems = new ArrayList<>();
		String query = "FROM ItemDAO i where i.name = '14oz Cola'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		dbCon.updatePosInMenuTable(newItems, "MAIN_PAGE");
		
		newItems = new ArrayList<>();
		query = "FROM ItemDAO i where i.name = '14oz Cola'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		query = "FROM ItemDAO i where i.name = '14oz Diet Cola'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		query = "FROM ItemDAO i where i.name = '4oz Dash Cola'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		query = "FROM ItemDAO i where i.name = '4oz Dash Diet Cola'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		query = "FROM ItemDAO i where i.name = 'Pint Cola'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		query = "FROM ItemDAO i where i.name = 'Pint Diet Cola'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		dbCon.updatePosInMenuTable(newItems, "MIN_MIX_PAGE");

		newItems = new ArrayList<>();
		query = "FROM ItemDAO i where i.name = 'Cheese Pie'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		query = "FROM ItemDAO i where i.name = 'Beef Lasagne'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		dbCon.updatePosInMenuTable(newItems, "FOOD_PAGE");

		newItems = new ArrayList<>();
		query = "FROM ItemDAO i where i.name = 'Beef Burger'";
		newItems.add(dbCon.getFirst(ItemDAO.class, query));
		dbCon.updatePosInMenuTable(newItems, "BURGER_PAGE");
		

//		query = "SELECT distinct i FROM ItemDAO i " 
//      + "JOIN i.menuPages p "
//      + "WHERE p.name = " 
//      + "'FOOD_PAGE'";
//		
		query = "SELECT i FROM MenuPageDAO i " ;
//			      + "JOIN i.itemDAO "
			//      + "WHERE i.name = " 
			  //    + ":page";
		
		logger.info(query);
		Map<String, String> params = new HashMap<>();
//		params.put("page", "FOOD_PAGE");
		List<MenuPageDAO> item = dbCon.query(query, params);
	}
	
	

}
