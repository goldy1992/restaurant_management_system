package com.github.goldy1992.rms.client.frontend.till.tillMenu.barTabMenu;

import com.github.goldy1992.rms.client.backend.MessageSender;
import com.github.goldy1992.rms.client.frontend.till.tillMenu.TillMenuView;
import com.github.goldy1992.rms.message.EventNotification.TableStatusEvtNfn;
import com.github.goldy1992.rms.message.Response.TableStatusResponse;
import com.github.goldy1992.rms.message.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Mike on 12/09/2016.
 */
@MessageEndpoint
public class BarTabMenuController implements ActionListener {

    private BarTabDialogView view;
    private BarTabMenuModel model;

    @Autowired
    private MessageSender messageSender;

    public BarTabMenuController() { }

    public Integer getTabNumber(TillMenuView tillMenuView, boolean modal, BarTabMenuModel.Functionality functionality) {
        TableStatusResponse response =  this.messageSender.sendTableStatusRequest(new ArrayList<>());
        if (null == response.getTableStatuses() || response.getTableStatuses().size() <= 0 ) {
            return null;
        }

        model = new BarTabMenuModel();
        getModel().setFunctionality(functionality);
        getModel().init(response.getTableStatuses(), functionality);
        view = new BarTabDialogView(tillMenuView, modal);
        getView().init(getModel().getCurrentStatuses(), this, functionality);
        getView().setVisible(true);
        return getModel().getChosenTab();
    }

    public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender;  }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof TabJButton) {
            TabJButton tabJButton = (TabJButton) e.getSource();
            keyTabJButtonAction(tabJButton);
        } else if (e.getSource() instanceof JButton) {
            JButton jButton = (JButton) e.getSource();
            if (jButton.getText().equals("New Tab")) {
                newTabAction();
            } else if (jButton.getText().equals("Cancel")) {
                getView().dispose();
                view = null;
            } // else if
        }
    } // actionPerformed

    private void newTabAction() {
        EnterQuantityDialog chooseNewTab = new EnterQuantityDialog(getView(), true);
        chooseNewTab.setVisible(true);
        final int chosenTable = chooseNewTab.getValue();

        if (chosenTable <= 0  || getModel().getCurrentStatuses().containsKey(chosenTable)) {
            JOptionPane.showMessageDialog(getView(), "Invalid Tab Number.");
        } else {
            getModel().setChosenTab(chosenTable);
            getView().dispose();
        } //else
    } // newTabAction

    private void keyTabJButtonAction(TabJButton tabJButton) {
        if (getModel().getCurrentStatuses().get(tabJButton.getTabNumber()) == Table.TableStatus.IN_USE) {
            JOptionPane.showMessageDialog(getView(), "Tab " + tabJButton.getTabNumber() + " is currently in use!" );
            return;
        } // if
        getModel().setChosenTab(tabJButton.getTabNumber());
        getView().dispose();
        view = null;
    } // keyTabJButtonAction

    @ServiceActivator(inputChannel="tableStatusEvtNotificationChannel")
    public void setTableStatus(TableStatusEvtNfn tableStatusEvtNfn) {
        if (null != model) {
            model.getCurrentStatuses().put(tableStatusEvtNfn.getTableNumber(), tableStatusEvtNfn.getTableStatus());
        }
        if (null != view) {
            view.init(getModel().getCurrentStatuses(), this, getModel().getFunctionality());
        }
    }

    public BarTabDialogView getView() { return view; }

    public BarTabMenuModel getModel() {        return model; }
} // class