package com.mike.client.gui;

import com.mike.client.frontend.MainMenu.View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Created by Mike on 2/12/2017.
 */
public class MenuViewPage {

    private MenuView menuView;

    protected MenuViewPage(){}
    public MenuViewPage(MenuView menuView) {
        this.menuView = menuView;
    }

    public JButton getOperationButton(String buttonName) {
        ArrayList<JButton> buttons = menuView.getButtons();
        JButton sendOrderButton = null;
        for (JButton jb : buttons) {
            if (jb.getText().equals(buttonName)) {
                sendOrderButton = jb;
            }
        }
        return sendOrderButton;
    }

    public void selectMenuItem(String buttonName, int quantity) {
        MenuView mv = this.menuView;

        mv.getCardPanel().getName();
        while (!mv.currentCard.getName().equals("MAIN_PAGE")) {
            MouseEvent me = new MouseEvent((Component)mv.getOutputTextPane(), MouseEvent.MOUSE_CLICKED, 0, // no modifiers
                    10, 10, // where: at (10, 10}
                    1, 1, false, 0);
            mv.getOutputTextPane().dispatchEvent(me);
        }

        MenuItemJButton buttonToSelect = null;

        for (MenuItemJButton mib: mv.getMenuItemButtons()) {
            if (mib.getText().equals(buttonName)) {
                buttonToSelect = mib;
                break;
            }
        }

        java.util.List menuCardPanelList = new ArrayList<MenuCardPanel>();

        for (MenuCardPanel mcp : mv.getCardPanelsList()) {
            if (mcp.getCardMenuItems().contains(buttonToSelect)) {
                MenuCardPanel currentPanel = mcp;
                while (null != currentPanel.getParentPanel()){
                    menuCardPanelList.add(currentPanel);
                    currentPanel = currentPanel.getParentPanel();
                }
                break;
            }
        }

        for (int i = menuCardPanelList.size() - 1; i >= 0; i-- ) {
            java.util.List<MenuCardLinkJButton> menuCardLinkJButtonList = mv.currentCard.getChildCardButtons();
            for (MenuCardLinkJButton mb : menuCardLinkJButtonList) {
                MenuCardPanel mcp = (MenuCardPanel)menuCardPanelList.get(i);
                if (mb.getTargetPanel().getName().equals(mcp.getName())) {
                    mb.doClick();
                    break;
                }
            }
        }


        java.util.List<MenuItemJButton> menuItemJButtonList = mv.currentCard.getCardMenuItems();

        if (quantity > 1) {
            String quantityy = Integer.toString(quantity);
            for (char c : quantityy.toCharArray()) {
                KeypadPanelJButton keypadPanelJButton = (KeypadPanelJButton) mv.currentCard.getKeypadPanel().getComponent(quantity-1);
                keypadPanelJButton.doClick();
            }
        }

        for (MenuItemJButton mij: menuItemJButtonList) {
            if (mij.getText().equals(buttonName)) {
                mij.doClick();
            }
        }

    }
}
