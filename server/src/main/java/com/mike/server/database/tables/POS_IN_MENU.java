package com.mike.server.database.tables;

import java.io.Serializable;

public class POS_IN_MENU implements Serializable {

	private Long id;
	private String location;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
