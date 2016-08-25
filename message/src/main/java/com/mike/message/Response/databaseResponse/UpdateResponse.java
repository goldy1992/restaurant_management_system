package com.mike.message.Response.databaseResponse;

import com.mike.message.Request.databaseRequest.Update;
import com.mike.message.Response.Response;

/**
 * Created by michaelg on 25/08/2016.
 */
public class UpdateResponse extends Response {
	private boolean success;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public UpdateResponse(Update request, boolean success) {
		super(request);
		this.success = success;
	}
}
