package com.mike.client.frontend.till.tillMenu;

import com.mike.client.frontend.MainMenu.Model.MenuModel;
import com.mike.item.Tab;
import com.mike.item.dbItem.MenuPageDAO;

import java.util.List;

/**
 * Created by michaelg on 07/09/2016.
 */
public class TillMenuModel extends MenuModel {

	private boolean tabLoaded = false;

	public TillMenuModel() { super(); }

	@Override
	public void init(List<MenuPageDAO> menuPageDAOs, Tab tab) {
		super.init(menuPageDAOs, tab);
	}

	public boolean isTabLoaded() { return tabLoaded; }
	public void setTabLoaded(boolean tabLoaded) { this.tabLoaded = tabLoaded; }
}
