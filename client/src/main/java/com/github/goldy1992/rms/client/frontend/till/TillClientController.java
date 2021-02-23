package com.github.goldy1992.rms.client.frontend.till;

import com.github.goldy1992.rms.client.backend.MessageSender;
import com.github.goldy1992.rms.client.frontend.till.tillMenu.TillMenuController;
import com.github.goldy1992.rms.item.Tab;
import com.github.goldy1992.rms.message.Request.RegisterClientRequest;
import com.github.goldy1992.rms.message.Response.RegisterClientResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Created by michaelg on 06/09/2016.
 */
public class TillClientController implements ActionListener {

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private TillMenuController tillMenuController;
	private TillView view;
	private TillModel model;
	private Float change = null;

	public void init() {
		RegisterClientResponse registerClientResponse = messageSender.registerClient(RegisterClientRequest.ClientType.TILL);

		if (!registerClientResponse.hasPermission()) {
			System.exit(0);
		} // if

		view = new TillView(this);
		model = new TillModel();
		getView().setVisible(true);
	} // init

	public TillView getView() { return view; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() instanceof JButton) {
			if (ae.getSource() == view.getStartClientButton()) {
				this.setChange(null);
				getTillMenuController().initTillMenu(this, view, model.getCurrentTab());
			} // if
		}
	} // actionPerformed

	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	public void setTillMenuController(TillMenuController tillMenuController) { this.tillMenuController = tillMenuController; }

	public Float getChange() {	return change; }

	public void setChange(Float change) {
		if (null == change) {
			view.getChangeOutputLabel().setText("");
			this.change = null;
		} else {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			view.getChangeOutputLabel().setText("£ " + df.format(change));
			this.change = change;
		}
	} // setChange

	public void updateTab(Tab tab) {
		model.setCurrentTab(tab);
	}

	public void setPreviousTab(Tab tab) {
		model.setPreviousTab(tab);
	}

	public TillMenuController getTillMenuController() { return tillMenuController; }
	public TillModel getTillModel() { return model; }
}
