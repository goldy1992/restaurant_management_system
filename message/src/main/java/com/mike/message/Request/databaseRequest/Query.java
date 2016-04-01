package com.mike.message.Request.databaseRequest;

import com.mike.message.Request.Request;

public class Query extends Request {
	
	private final String query;
	
	public Query(String query) {
		super();
		this.query = query;
	}

	public String getQuery() {
		return query;
	}
	
	

}
