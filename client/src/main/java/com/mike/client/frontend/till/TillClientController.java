package com.mike.client.frontend.till;

import com.mike.client.backend.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by michaelg on 06/09/2016.
 */
public class TillClientController {

	@Autowired
	public MessageSender messageSender;

	private TillView view;

	public void init() {
		view = new TillView();
		getView().setVisible(true);
	}

	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }

	public TillView getView() { return view; }
}
