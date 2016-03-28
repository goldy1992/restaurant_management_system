package com.mike.server.database.tables;

import java.util.HashSet;
import java.util.Set;

public class MENU_PAGE {

	private String name;
	private String parentPageId;
	private String buttonName;
	
	private Set<ITEMS> items = new HashSet<>(0);
	
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
	
	public static MENU_PAGE createMenuPage(String name, String parentPageId, String buttonName) {
		MENU_PAGE menuPage = new MENU_PAGE();
		menuPage.setName(name);
		menuPage.setButtonName(buttonName);
		menuPage.setParentPageId(parentPageId);
		return menuPage;
	}
	public Set<ITEMS> getItems() {
		return items;
	}
	public void setItems(Set<ITEMS> items) {
		this.items = items;
	}
}
