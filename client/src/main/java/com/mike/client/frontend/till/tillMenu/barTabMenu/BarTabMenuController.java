package com.mike.client.frontend.till.tillMenu.barTabMenu;

import com.mike.client.backend.MessageSender;
import com.mike.client.frontend.MainMenu.View.KeyJButton;
import com.mike.client.frontend.till.tillMenu.TillMenuView;
import com.mike.message.Response.TableStatusResponse;
import com.mike.message.Table;
import org.springframework.beans.factory.annotation.Autowired;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Mike on 12/09/2016.
 */
public class BarTabMenuController implements ActionListener {

    BarTabDialogView view;
    BarTabMenuModel model;

    @Autowired
    private MessageSender messageSender;

    public BarTabMenuController() { }

    public Integer getTabNumber(TillMenuView tillMenuView, boolean modal, BarTabMenuModel.Functionality functionality) {
        TableStatusResponse response =  this.messageSender.sendTableStatusRequest(new ArrayList<>());
        if (null == response.getTableStatuses() || response.getTableStatuses().size() <= 0 ) {
            return null;
        }

        model = new BarTabMenuModel();
        model.init(response.getTableStatuses(), functionality);
        view = new BarTabDialogView(this, tillMenuView, modal);
        view.init(model.getCurrentStatuses(), functionality);
        view.setVisible(true);
        return model.getChosenTab();
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
                view.dispose();
            } // else if
        }
    } // actionPerformed

    private void newTabAction() {
        EnterQuantityDialog chooseNewTab = new EnterQuantityDialog(view, true);
        chooseNewTab.setVisible(true);
        final int chosenTable = chooseNewTab.getValue();

        if (chosenTable <= 0  || model.getCurrentStatuses().containsKey(chosenTable)) {
            JOptionPane.showMessageDialog(view, "Invalid Tab Number.");
        } else {
            model.setChosenTab(chosenTable);
            view.dispose();
        } //else
    } // newTabAction

    private void keyTabJButtonAction(TabJButton tabJButton) {
        if (model.getCurrentStatuses().get(tabJButton.getTabNumber()) == Table.TableStatus.IN_USE) {
            JOptionPane.showMessageDialog(view, "Tab " + tabJButton.getTabNumber() + " is currently in use!" );
            return;
        } // if
        /* SEND A NOTIFICATION TO EVERYONE ELSE THAT TABLE IS NOW
        IN USE */
        messageSender.sendTableStatusEventNotification(tabJButton.getTabNumber(), Table.TableStatus.IN_USE);
        model.setChosenTab(tabJButton.getTabNumber());
        view.dispose();
    } // keyTabJButtonAction

} // class
