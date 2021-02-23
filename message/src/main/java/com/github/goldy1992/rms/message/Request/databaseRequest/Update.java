package com.github.goldy1992.rms.message.Request.databaseRequest;

import com.github.goldy1992.rms.message.Request.Request;

import java.io.Serializable;

/**
 * Created by michaelg on 25/08/2016.
 */
public class Update<T extends Serializable> extends Request {

	private final T item;

	public Update(T item) {
		super();
		this.item = item;
	}

	public T getItem() {return item; }
} // class
