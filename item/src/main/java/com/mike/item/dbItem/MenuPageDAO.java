package com.mike.item.dbItem;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MenuPageDAO implements Serializable {

	private String name;
	private String parentPageId;
	private String buttonName;
	
	private Collection<ItemDAO> itemDAO;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentPageId() {
		return parentPageId;
	}
	public void setParentPageId(String pageId) {
		this.parentPageId = pageId;
	}
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}	
	
	public static MenuPageDAO createMenuPage(String name, String parentPageId, String buttonName) {
		MenuPageDAO menuPage = new MenuPageDAO();
		menuPage.setName(name);
		menuPage.setButtonName(buttonName);
		menuPage.setParentPageId(parentPageId);
		return menuPage;
	}
	public Collection<ItemDAO> getItems() {
		return itemDAO;
	}
	public void setItems(Collection<ItemDAO> itemDAO) {
		this.itemDAO = itemDAO;
	}
}
