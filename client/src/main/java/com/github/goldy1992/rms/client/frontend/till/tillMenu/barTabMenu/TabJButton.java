package com.github.goldy1992.rms.client.frontend.till.tillMenu.barTabMenu;

import javax.swing.*;

/**
 * Created by Mike on 14/09/2016.
 */
public class TabJButton extends JButton {

    private int tabNumber;

    public TabJButton(int tabNumber) {
        super("table " + tabNumber);
        this.setTabNumber(tabNumber);
    }

    public int getTabNumber() { return tabNumber; }
    public void setTabNumber(int tabNumber) { this.tabNumber = tabNumber; }
}
