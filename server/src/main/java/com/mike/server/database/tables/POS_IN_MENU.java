package com.mike.server.database.tables;

import java.io.Serializable;

public class POS_IN_MENU implements Serializable {

	private long id;
	private String location;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public static POS_IN_MENU createMenuPage(Long id, String location) {
		POS_IN_MENU posInMenu = new POS_IN_MENU();
		posInMenu.setId(id);
		posInMenu.setLocation(location);
		return posInMenu;
}
}
