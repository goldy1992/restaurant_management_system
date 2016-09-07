package com.mike.client.frontend.till;

import com.mike.client.backend.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by michaelg on 06/09/2016.
 */
public class TillClientController implements ActionListener {

	@Autowired
	public MessageSender messageSender;

	private TillView view;

	public void init() {
		view = new TillView();
		getView().setVisible(true);
	}

	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }

	public TillView getView() { return view; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == view.getStartClientButton()) {
			view.setVisible(true);
		} // if
	} // actionPerformed
}
