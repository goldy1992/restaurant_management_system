package com.mike.client.MainMenu.Model;

import java.util.List;

public class MenuPage {

	private final String name;
	private MenuPage parentPage;
	private List<MenuItem> menuItems;
	private List<MenuPage> childMenuPages;

	public MenuPage(String name) {	this.name = name; }
	public MenuPage getParentPage() { return parentPage; }

	public void setParentPage(MenuPage parentPage) { this.parentPage = parentPage; }

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public List<MenuPage> getChildMenuPages() {
		return childMenuPages;
	}

	public void setChildMenuPages(List<MenuPage> childMenuPages) {
		this.childMenuPages = childMenuPages;
	}

	public String getName() {
		return name;
	}
}
