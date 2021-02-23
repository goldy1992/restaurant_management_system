package com.github.goldy1992.rms.client.frontend.till.tillMenu;

import com.github.goldy1992.rms.client.frontend.MainMenu.Model.MenuModel;
import com.github.goldy1992.rms.item.Tab;
import com.github.goldy1992.rms.item.dbItem.MenuPageDAO;

import java.util.List;

/**
 * Created by michaelg on 07/09/2016.
 */
public class TillMenuModel extends MenuModel {

	private boolean tabLoaded = false;

	public TillMenuModel(Tab tab) {
		super();
		setUpTab(tab);
	}

	@Override
	public void init(List<MenuPageDAO> menuPageDAOs, Tab tab) {
		super.init(menuPageDAOs, tab);
	}

	public boolean isTabLoaded() { return tabLoaded; }
	public void setTabLoaded(boolean tabLoaded) { this.tabLoaded = tabLoaded; }
}
