package com.mike.client.gui;

import com.mike.client.frontend.till.tillMenu.barTabMenu.BarTabDialogView;
import com.mike.client.frontend.till.tillMenu.barTabMenu.BarTabMenuController;
import com.mike.client.frontend.till.tillMenu.barTabMenu.TabJButton;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;

import javax.swing.*;

/**
 * Created by Mike on 2/16/2017.
 */
public class SelectTabPage {

    private BarTabDialogView barTabDialogView;

    public SelectTabPage(BarTabMenuController barTabMenuController, JButton barTabButton) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                barTabButton.doClick();
            }
        });
        t.start();

        Pause.pause(new Condition("waiting for view to appear") {
            @Override
            public boolean test() {
                if (barTabMenuController.getView() == null) {
                    return false;
                }

                if (!barTabMenuController.getView().isVisible()) {
                    return false;
                }
                return true;
            }
        });

        barTabDialogView = barTabMenuController.getView();
    }

    public JButton getTable(int number) {
        for (TabJButton t : barTabDialogView.getTabJButtons()) {
            if (number == t.getTabNumber()) {
                return t;
            }
        }
        return null;
    }






}
