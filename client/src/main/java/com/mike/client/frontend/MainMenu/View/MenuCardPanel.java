/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.frontend.MainMenu.View;

import com.mike.client.frontend.MainMenu.Factors;
import com.mike.client.frontend.MainMenu.Model.MenuPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author mbbx9mg3
 */
public class MenuCardPanel extends JPanel {
    private MenuCardPanel parentPanel;
    private final MenuView belongsToMenuView;
    private final ArrayList<MenuItemJButton> cardMenuItems;
    private final JPanel itemsPanel;
    private final MenuPage menuPage;
    private final ArrayList<MenuCardLinkJButton> childCardButtons;
    private final JPanel menuSelectPanel;
    private JPanel keypadPanel;


    /**
     * @param parentMenuView
     */
    public MenuCardPanel(MenuView parentMenuView, MenuPage menuPage) {
        super();
        this.menuPage = menuPage;

        itemsPanel = new JPanel();
        cardMenuItems = new ArrayList<>();
//		if (null != menuPage && null != menuPage.getMenuItems()) {
//			for (MenuItem menuItem : menuPage.getMenuItems()) {
//				cardMenuItems.add(MenuItemJButton.createMenuItemJButton(menuItem, this, parentMenu));
//			}
//			for (MenuItemJButton mij : cardMenuItems) {
//				itemsPanel.add(mij);
//			}
//		}

        menuSelectPanel = new JPanel();
        childCardButtons = new ArrayList<>();
//		if (null != menuPage && null != menuPage.getChildMenuPages()) {
//			for (MenuPage cardMenuPage : menuPage.getChildMenuPages()) {
//				childCardButtons.add(MenuCardLinkJButton.createMenuCardLinkButton(this, cardMenuPage.getButtonName(), parentMenu));
//			}
//			for (MenuCardLinkJButton menuCardLinkJButton : childCardButtons) {
//				menuSelectPanel.add(menuCardLinkJButton);
//			}
//		}

        // true because we want to use the keyboard to control the quantity
        setKeypadPanel(null);
        this.belongsToMenuView = parentMenuView;
        this.addMouseListener(MenuView.menuController);
    }

    public MenuCardPanel() {
        this(null, null);
    }

    /**
     * @param panel
     */
    public void setParentPanel(MenuCardPanel panel) {
        this.parentPanel = panel;
    }

    @Override
    public String toString() {
        String toReturn = "Name: " + this.getName() + "\n";

        if (this.parentPanel == null)
            toReturn += "Parent panel: null\n";
        else
            toReturn += "Parent panel: " + parentPanel.getName() + "\n";

        toReturn += "buttons(" + cardMenuItems.size() + ") : ";
        for (MenuItemJButton m : cardMenuItems)
            toReturn += m.getText() + "  ";
        toReturn += "\n";

        toReturn += "child panels (" + childCardButtons.size() + ") : ";
        for (MenuCardLinkJButton m : childCardButtons)
            toReturn += m.getTargetPanel().getName() + "  ";
        toReturn += "\n";

        return toReturn;

    }

    /**
     * Adds a menu item button to the ArrayList of all menu items on the card
     *
     * @param button
     */
    public void addMenuItemButton(MenuItemJButton button) {
        this.cardMenuItems.add(button);
    }

    /**
     * Takes the arraylist of all the menu item buttons and adds them to the
     * menu Item Panel
     *
     * @return true if done successfully, false otherwise
     */
    public boolean addAllItemsToPanel() {
        if (cardMenuItems == null || cardMenuItems.isEmpty())
            return false;

        int size = cardMenuItems.size();

        int[] gridDimensions = Factors.closestIntSquare(size);
        itemsPanel.setLayout(new GridLayout(gridDimensions[0], gridDimensions[1]));

        for (MenuItemJButton b : cardMenuItems)
            itemsPanel.add(b);

        return true;
    }

    /**
     * @param button
     */
    public void addChildCardButton(MenuCardLinkJButton button) {
        childCardButtons.add(button);
    }

    /**
     * @return
     */
    public boolean addAllChildCardButtonsToPanel() {
        if (childCardButtons == null || childCardButtons.isEmpty()) {
            return false;
        }

        int size = childCardButtons.size();

        int[] gridDimensions = Factors.closestIntSquare(size);
        menuSelectPanel.setLayout(new GridLayout(gridDimensions[0], gridDimensions[1]));

        for (MenuCardLinkJButton b : childCardButtons)
            menuSelectPanel.add(b);

        return true;
    }


    public ArrayList<MenuItemJButton> getCardMenuItems() {
        return cardMenuItems;
    }

    public MenuCardPanel getParentPanel() {
        return parentPanel;
    }

    public ArrayList<MenuCardLinkJButton> getChildCardButtons() {
        return childCardButtons;
    }

    public JPanel getMenuSelectPanel() {
        return menuSelectPanel;
    }

    public JPanel getItemsPanel() {
        return itemsPanel;
    }

    public JPanel getKeypadPanel() {
        return keypadPanel;
    }

    /**
     * This method should return true for every panel and false for the main
     * panel.
     *
     * @return true if the Panel has a parent, false if not.
     */
    public boolean hasParent() {
        return (parentPanel != null);
    }

    /**
     * A factory method to make a new Menu Card Panel
     *
     * @param parentMenu
     * @return a new Menu Card Panel
     */
    public static MenuCardPanel createMenuCardPanel(JDialog parentMenu, MenuPage menuPage) {
        MenuCardPanel x = new MenuCardPanel((MenuView) parentMenu, menuPage);
        x.setLayout(new GridLayout(1, 0));
        x.add(x.getMenuSelectPanel());
        x.add(x.getItemsPanel());
        //x.add(x.getKeypadPanel());
        x.setFocusable(false);
        x.setRequestFocusEnabled(false);
        return x;
    }

    public MenuPage getMenuPage() {
        return menuPage;
    }

    public void setKeypadPanel(JPanel keypadPanel) { this.keypadPanel = keypadPanel; }
} // class


