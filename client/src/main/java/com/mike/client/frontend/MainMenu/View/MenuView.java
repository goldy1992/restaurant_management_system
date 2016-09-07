package com.mike.client.frontend.MainMenu.View;

import com.mike.client.frontend.MainMenu.MenuController;
import com.mike.client.frontend.MainMenu.Model.MenuModel;
import com.mike.client.frontend.till.TillMenuView;
import com.mike.item.Tab;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * <p>This class displays the Menu Dialog box of the restaurant management System.
 * It contains a menu bar of which underneath is the main JPanel called the
 * FormPanel. The form panel contains two extra panels called the OutputAreaPanel,
 * and the card panel.</p>
 * <p/>
 * <p><b>OutputAreaPanel</b> - When an item is selected on the menu it will appear
 * in the panel's JFrame along with it's price and the amount that has been
 * ordered; the total price will appear too. A scroll bar implementation will
 * also be included to see previous purchases that have disappeared from the
 * screen.</p>
 * <p/>
 * <p><b>CardPanel</b> - The car panel will contain the actual Menu; it works
 * as a <a href="http://docs.oracle.com/javase/7/docs/api/java/awt/CardLayout.html">CardLayout</a>
 * in a tree format. The page you see originally is the main panel and has no parent,
 * i.e. it is the root of the card tree. Every other panel has one parent and when
 * an area on the screen that is not a menu item is clicked the card shown will
 * change to the current panel's parent. Each card will contain all the relevant
 * items and all this information will be requested from the database during the
 * creation of the menu.</p>
 *
 * @author mbbx9mg3
 */
public class MenuView extends JDialog {
	protected final ArrayList<JComponent> components = new ArrayList<>();
	protected final ArrayList<JButton> buttons = new ArrayList<>();
	protected final ArrayList<JPanel> panels = new ArrayList<>();
	protected final ArrayList<MenuItemJButton> menuItemButtons = new ArrayList<>();

	protected final MenuCardPanel kitchenBarMsgPanel;
	private ArrayList<MenuCardPanel> cardPanelsList = new ArrayList<>();
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel CardPanel;
	private javax.swing.JPanel FormPanel;
	private javax.swing.JMenuBar MenuBar;
	private javax.swing.JTextPane OutputArea;
	private javax.swing.JPanel OutputAreaPanel;
	private javax.swing.JMenu editMenu;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane ouputScrollPane;
	private javax.swing.JTextPane quantityArea;
	private javax.swing.JScrollPane quantityPane;
	private javax.swing.JTextPane totalCostArea;
	private javax.swing.JScrollPane totalCostPane;
	// End of variables declaration//GEN-END:variables

	private JTextPane outputTextPane;
	private JTextPane quantityTextPane;
	public JTextPane totalCostTextPane;
	public int quantitySelected;
	public MenuCardPanel currentCard;
	public static MenuController menuController;

	/**
	 * Creates new form Menu
	 *
	 * @param parent
	 * @param modal
	 * @param tab
	 * @throws java.sql.SQLException
	 */
	public MenuView(MenuController menuController, java.awt.Frame parent, MenuModel menuModel, boolean modal, Tab tab) {

		super(parent, modal);

		MenuView.menuController = menuController;
		// initialises the part of the GUI made automatically by netbeans
		initComponents();
		outputTextPane.addMouseListener(menuController);

		// create the kitchen/Bar message panel
		kitchenBarMsgPanel = createKitchenBarMessageCard();

		// add the kitchen/Bar message panel to the card layout
		CardPanel.add(kitchenBarMsgPanel, kitchenBarMsgPanel.getName());

		// prepare the rest of the cards and store in cardPanels arrayList
		//cardPanelsList = getCards();
		cardPanelsList = MenuViewBuilderHelper.createCardPanelsForView(this, menuModel);

		// initialise the cardLayout to show the main panel
		cardPanelsList.set(0, initialiseMainCard(cardPanelsList.get(0)));

		for (MenuCardPanel p : cardPanelsList) {
			CardPanel.add(p, p.getName());
			p.add(KeypadJPanel.createKeypadPanel());
		}

		//MyClient.debugGUI.addText("show");
		CardLayout cl = (CardLayout) (CardPanel.getLayout());
		cl.show(CardPanel, cardPanelsList.get(0).getName());
		currentCard = cardPanelsList.get(0);

		/* set the kitchen card's parent now so that it is not null when it's it
		first selected */
		kitchenBarMsgPanel.setParentPanel(currentCard);

		// this code only allows the output Area text pane to have key controls
		components.addAll(buttons);
		components.addAll(menuItemButtons);
		components.addAll(cardPanelsList);

		for (JComponent t : components) {
			t.setFocusable(false);
			t.requestFocus(false);
		} // for

		setUpTab(tab);
	} // constructor

	protected String[] getOptionNames() {

		String[] x = {"Print Bill",
				"Void", "Void Last Item", "Split Bill",
				"Order On Hold", "Delivered", "Send Order"};
		return x;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel2 = new javax.swing.JPanel();
		FormPanel = new javax.swing.JPanel();
		OutputAreaPanel = new javax.swing.JPanel();
		ouputScrollPane = new javax.swing.JScrollPane();
		setOutputArea(new JTextPane());
		quantityPane = new javax.swing.JScrollPane();
		quantityArea = new javax.swing.JTextPane();
		totalCostPane = new javax.swing.JScrollPane();
		totalCostArea = new javax.swing.JTextPane();
		CardPanel = new javax.swing.JPanel();
		MenuBar = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		editMenu = new javax.swing.JMenu();

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 165, Short.MAX_VALUE)
		);
		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 63, Short.MAX_VALUE)
		);

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new java.awt.GridLayout(1, 0));

		FormPanel.setPreferredSize(new java.awt.Dimension(400, 500));
		FormPanel.setLayout(new java.awt.GridBagLayout());

		OutputAreaPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		OutputAreaPanel.setFocusable(false);
		OutputAreaPanel.setPreferredSize(new java.awt.Dimension(400, 150));
		OutputAreaPanel.setLayout(new java.awt.GridBagLayout());

		setOutputTextPane(getOutputArea());
		getOutputArea().setEditable(false);
		ouputScrollPane.setViewportView(getOutputArea());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 2631;
		gridBagConstraints.ipady = 108;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
		OutputAreaPanel.add(ouputScrollPane, gridBagConstraints);

		setQuantityTextPane(quantityArea);
		quantityPane.setViewportView(quantityArea);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
		gridBagConstraints.weightx = 0.5;
		OutputAreaPanel.add(quantityPane, gridBagConstraints);

		totalCostTextPane = totalCostArea;
		totalCostPane.setViewportView(totalCostArea);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0.5;
		OutputAreaPanel.add(totalCostPane, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 0.3;
		FormPanel.add(OutputAreaPanel, gridBagConstraints);
		panels.add(OutputAreaPanel);

		CardPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		CardPanel.setName(""); // NOI18N
		CardPanel.setPreferredSize(new java.awt.Dimension(400, 350));
		CardPanel.setLayout(new java.awt.CardLayout());
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 0.7;
		FormPanel.add(CardPanel, gridBagConstraints);
		panels.add(CardPanel);

		getContentPane().add(FormPanel);
		panels.add(FormPanel);

		fileMenu.setText("File");
		MenuBar.add(fileMenu);

		editMenu.setText("Edit");
		MenuBar.add(editMenu);

		setJMenuBar(MenuBar);
		components.add(MenuBar);

		MenuBar.setFocusable(false);
		MenuBar.requestFocus(false);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * The method that makes the Kitchen/Bar message card with the keyboard
	 *
	 * @return the newly created card
	 */
	protected final MenuCardPanel createKitchenBarMessageCard() {
		MenuCardPanel containerPanel = MenuCardPanel.createMenuCardPanel(this, null);
		containerPanel.setName("kitchenBarPanel");
		containerPanel.removeAll();

		String[] ch = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
				"A", "S", "D", "F", "G", "H", "J", "K", "L",
				"Z", "X", "C", "V", "B", "N", "M",
				"SPACE"};

		containerPanel.setLayout(new GridLayout(5, 1));
       
        /*  ADD THE NUMBERS */
		JPanel numberLine = new JPanel();
		numberLine.setLayout(new GridLayout(1, 10));
		containerPanel.add(numberLine);
		for (int i = 1; i <= 10; i++) {
			Integer x = i;
			if (x == 10) x = 0;
			KeyJButton key = new KeyJButton(x.toString(), x.toString().charAt(0));
			key.addActionListener(menuController);
			numberLine.add(key);
		}

		// add line 1
		JPanel line1 = new JPanel();
		line1.setLayout(new GridLayout(1, 10));
		containerPanel.add(line1);
		for (int i = 0; i <= 9; i++) {
			KeyJButton key = new KeyJButton(ch[i], ch[i].charAt(0));
			key.addActionListener(menuController);
			line1.add(key);
		} // for

		// add line 2
		JPanel line2 = new JPanel();
		line2.setLayout(new GridLayout(1, 10));
		containerPanel.add(line2);
		for (int i = 10; i <= 18; i++) {
			KeyJButton key = new KeyJButton(ch[i], ch[i].charAt(0));
			key.addActionListener(menuController);
			line2.add(key);
		}

		// add line 3
		JPanel line3 = new JPanel();
		line3.setLayout(new GridLayout(1, 10));
		containerPanel.add(line3);
		for (int i = 19; i <= 25; i++) {
			KeyJButton key = new KeyJButton(ch[i], ch[i].charAt(0));
			key.addActionListener(menuController);
			line3.add(key);
		}

		JPanel line4 = new JPanel();
		line4.setLayout(new GridLayout(1, 1));
		containerPanel.add(line4);
		// La ultima en la colección
		JButton key = new KeyJButton("SPACE", ' ');
		key.addActionListener(menuController);
		line4.add(key);

		return containerPanel;
	}

	public final void setUpTab(Tab tab) {
		if (tab != null) {
			getOutputTextPane().setText(tab.toString());
		}
	}

	public void setTotal(double total) {
		DecimalFormat df = new DecimalFormat("0.00");
		String totalAsString = df.format(total);
		this.totalCostArea.setText("Total: £" + totalAsString);
	}

	public void addNumberToQuantity(int quantitySelected) {
		if (quantitySelected >= 0) {
			getQuantityTextPane().setText("" + quantitySelected);
		} else {
			getQuantityTextPane().setText("");
		}
	} // addNumberToQUantity

	/**
	 * Adds the functional features to the main panel
	 *
	 * @param panel
	 * @return the main panel with the extra buttons added to it
	 */
	protected final MenuCardPanel initialiseMainCard(MenuCardPanel panel) {
		// creates the panel that everything will be created on
		JPanel BillHandlePanel = new JPanel();

		BillHandlePanel.setBorder(new javax.swing.border.MatteBorder(null));
		BillHandlePanel.setFocusable(false);
		BillHandlePanel.setRequestFocusEnabled(false);
		// we just want one column, zero rows implies as many coulmns as we like
		GridLayout layout = new java.awt.GridLayout(0, 1);
		System.out.println(layout);
		BillHandlePanel.setLayout(layout);

		for (String s : getOptionNames()) {
			JButton newButton = new JButton();
			newButton.setText(s);
			newButton.addActionListener(menuController);
			BillHandlePanel.add(newButton);
			buttons.add(newButton);
		} // for

		System.out.println(layout);
		panel.add(BillHandlePanel);
		CardPanel.add(panel, panel.getName());
		panels.add(panel);
		return panel;
	} // initialiseCards

	/**
	 * Should be called when the screen detects a click that is not on an item
	 */
	public void switchToParentCard() {
		if (this.currentCard.hasParent()) {
			//             if (currentCard.getName().equals("kitchenBarPanel") && newTab.getNumberOfItems() > 0)
			if (currentCard.getName().equals("kitchenBarPanel")) {

				// detects to see if a number has been pressed and if so removes it
				String currentTab1 = getOutputTextPane().getText();
				String[] array = currentTab1.split("\n");

				//if (!isNumeric(array[array.length-1]))
				//  this.newItems.get(newItems.size()-1).setMessage(array[array.length-1]);
			} else {
				// detects to see if a number has been pressed and if so removes it
				String currentTab1 = getOutputTextPane().getText();
				String[] array = currentTab1.split("\n");

				if (!array[array.length - 1].equals("")) {
                        /* 
                        ADD CODE TO REMOVE TEXT ON THE LAST WRITTEN LINE
                        */
				}
			}


			//MyClient.debugGUI.addText("current panel " + this.currentCard.getName());
			int parentIndex = cardPanelsList.indexOf(this.currentCard.getParentPanel());
			CardLayout cl = (CardLayout) (CardPanel.getLayout());
			cl.show(CardPanel, cardPanelsList.get(parentIndex).getName());
			currentCard = cardPanelsList.get(parentIndex);


			// set the new Kitchen message parent to be the new card.
			kitchenBarMsgPanel.setParentPanel(currentCard);

			quantitySelected = -1;
			this.getQuantityTextPane().setText("");
		} // if
	} // switchToParentCard

	public static Class<?> findTypeOfParentMenu(Container cont) {
		int i = 1;
		while (cont.getParent() != null) {
			cont = cont.getParent();
			if (cont instanceof TillMenuView)
				return TillMenuView.class;
			if (cont instanceof MenuView)
				return MenuView.class;
			System.out.println("count: " + i);
			i++;
		}
		return null;
	}

	public static <T extends MenuView> T findParentMenu(Container cont) {
		while (cont.getParent() != null) {
			cont = cont.getParent();
			if (cont instanceof MenuView) {
				T returnT = (T) (MenuView) (Object) cont;
				return returnT;
			} else if (cont instanceof TillMenuView) {
				T returnT = (T) (TillMenuView) (Object) cont;
				return returnT;
			}
		}
		return null;
	}

	public JPanel getCardPanel() { return CardPanel; }
	public JTextPane getOutputArea() { return OutputArea; }
	public void setOutputArea(JTextPane outputArea) { OutputArea = outputArea; }
	public JTextPane getOutputTextPane() { return outputTextPane; }
	public void setOutputTextPane(JTextPane outputTextPane) { this.outputTextPane = outputTextPane; }
	public MenuCardPanel getKitchenBarMsgPanel() { return kitchenBarMsgPanel; }
	public JTextPane getQuantityTextPane() { return quantityTextPane; }
	public void setQuantityTextPane(JTextPane quantityTextPane) { this.quantityTextPane = quantityTextPane;	}

} // class