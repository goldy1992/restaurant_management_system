package com.github.goldy1992.rms.message.Request.databaseRequest;

import com.github.goldy1992.rms.message.Request.Request;

public class Query extends Request {
	
	private final String query;
	
	public Query(String query) {
		super();
		this.query = query;
	}

	public String getQuery() { return query; }
} // class
