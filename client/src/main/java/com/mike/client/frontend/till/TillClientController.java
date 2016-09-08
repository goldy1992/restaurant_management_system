package com.mike.client.frontend.till;

import com.mike.client.backend.MessageSender;
import com.mike.client.frontend.till.tillMenu.TillMenuController;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by michaelg on 06/09/2016.
 */
public class TillClientController implements ActionListener {

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private TillMenuController tillMenuController;

	private TillView view;

	public void init() {
		view = new TillView();
		tillMenuController.init(view, null);
		getView().setVisible(true);
	} // init



	public TillView getView() { return view; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == view.getStartClientButton()) {
			view.setVisible(true);
		} // if
	} // actionPerformed

	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	public void setTillMenuController(TillMenuController tillMenuController) { this.tillMenuController = tillMenuController; }
}
