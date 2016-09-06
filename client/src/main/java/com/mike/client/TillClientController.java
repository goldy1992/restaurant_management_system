package com.mike.client;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by michaelg on 06/09/2016.
 */
public class TillClientController {

	@Autowired
	public MessageSender messageSender;

	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
}
