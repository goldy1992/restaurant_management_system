package com.github.goldy1992.rms.message.Response.databaseResponse;

import com.github.goldy1992.rms.message.Request.databaseRequest.Update;
import com.github.goldy1992.rms.message.Request.databaseRequest.UpdateStock;
import com.github.goldy1992.rms.message.Response.Response;

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

	public UpdateResponse(UpdateStock request, boolean success) {
		super(request);
		this.success = success;
	}
}
