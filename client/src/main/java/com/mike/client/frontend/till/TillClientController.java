package com.mike.client.frontend.till;

import com.mike.client.backend.MessageSender;
import com.mike.client.frontend.till.tillMenu.TillMenuController;
import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Response.RegisterClientResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
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
		RegisterClientResponse registerClientResponse = messageSender.registerClient(RegisterClientRequest.ClientType.TILL);

		if (!registerClientResponse.hasPermission()) {
			System.exit(0);
		} // if

		view = new TillView(this);
		//tillMenuController.init(view, null);
		getView().setVisible(true);
	} // init



	public TillView getView() { return view; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() instanceof JButton) {
			if (ae.getSource() == view.getStartClientButton()) {
				tillMenuController.init(view, null);
			} // if
		}
	} // actionPerformed

	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	public void setTillMenuController(TillMenuController tillMenuController) { this.tillMenuController = tillMenuController; }
}
