package com.mike.client.gui;

import com.mike.client.frontend.MainMenu.MenuController;
import com.mike.client.frontend.SelectTableMenu.View.SelectTableView;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;

/**
 * Created by Mike on 2/12/2017.
 */
public class SelectTableViewPage {

    private SelectTableView selectTableView;

    public SelectTableViewPage(SelectTableView selectTableView) {
        this.selectTableView = selectTableView;
    }

    public void openTable(MenuController menuController) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                selectTableView.getOpenTable().doClick();
            }
        });
        t.start();

        Pause.pause(new Condition("waiting for view to appear") {
            @Override
            public boolean test() {
                if (menuController.getView() == null) {
                    return false;
                }

                if (!menuController.getView().isVisible()) {
                    return false;
                }
                return true;
            }
        });
    }

    public void clickTable(int tableNumber) {
        selectTableView.getTableButtons()[tableNumber].doClick();
    }
}
