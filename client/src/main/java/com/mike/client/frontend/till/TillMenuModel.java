package com.mike.client.frontend.till;

import com.mike.client.frontend.MainMenu.Model.MenuModel;

/**
 * Created by michaelg on 07/09/2016.
 */
public class TillMenuModel extends MenuModel {

	private boolean tabLoaded = false;

	public TillMenuModel() { super(); }

	public boolean isTabLoaded() { return tabLoaded; }
	public void setTabLoaded(boolean tabLoaded) { this.tabLoaded = tabLoaded; }
}
