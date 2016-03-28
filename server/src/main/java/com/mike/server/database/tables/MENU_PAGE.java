package com.mike.server.database.tables;

public class MENU_PAGE {

	private String name;
	private String parentPageId;
	private String buttonName;
	
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
}
