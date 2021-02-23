package com.github.goldy1992.rms.message.Response.databaseResponse;

import com.github.goldy1992.rms.message.Request.databaseRequest.Query;
import com.github.goldy1992.rms.message.Response.Response;

import java.util.List;

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
