package com.mike.message.Response.databaseResponse;

import java.util.List;

import com.mike.message.Request.databaseRequest.Query;
import com.mike.message.Response.Response;

public class QueryResponse extends Response {

	private List resultSet;
	public List getResultSet() {
		return resultSet;
	}
	public void setResultSet(List resultSet) {
		this.resultSet = resultSet;
	}
	public QueryResponse(Query request, List resultSet) {
		super(request);
		this.resultSet = resultSet;
		// TODO Auto-generated constructor stub
	}

}
