package com.mike.message.Request.databaseRequest;

import com.mike.message.Request.Request;

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
