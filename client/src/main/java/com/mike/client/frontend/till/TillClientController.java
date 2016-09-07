package com.mike.client.frontend.till;

import com.mike.client.backend.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by michaelg on 06/09/2016.
 */
public class TillClientController {

	@Autowired
	public MessageSender messageSender;

	private TillGUI view;
	public void init() {
		view = new TillGUI();
		view.setVisible(true);
	}

	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
}
