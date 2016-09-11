package com.mike.client.frontend.till.tillMenu;

import com.mike.client.frontend.MainMenu.MenuController;
import com.mike.client.frontend.MainMenu.Model.MenuModel;
import com.mike.client.frontend.MainMenu.View.BarTabDialogSelect;
import com.mike.client.frontend.MainMenu.View.MenuView;
import com.mike.client.frontend.Pair;
import com.mike.item.Tab;
import com.mike.message.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mbbx9mg3
 */
public class TillMenuView extends MenuView {
	/**
	 * Creates new form Menu
	 *
	 * @param menuController
	 * @param parent
	 * @param menuModel
	 * @param modal
	 * @param tab            @throws SQLException
	 */
	public TillMenuView(MenuController menuController, Frame parent, MenuModel menuModel, boolean modal, Tab tab) {
		super(menuController, parent, menuModel, modal, tab);
	}

    private BarTabDialogSelect selectorFrame;
    public boolean tabLoaded = false;

    @Override
    protected String[] getOptionNames() {
       String[] x = {"Print Bill", "Print Last Receipt", "Void",
           "Void Last Item",  "Split Bill", "Order On Hold", "Delivered",
           "Bar Tab", "Other Payment Methods", "Debit Card Pay", "Send Order"};
       return x;
    }
    public TillMenuView(TillMenuController tillMenuController, java.awt.Frame parent, MenuModel tillMenuModel, boolean modal) {
        super(tillMenuController, parent, tillMenuModel, modal, null);
    }



    public static TillMenuView makeMenu(){ //Client cParent, JFrame parent, Tab tab, TillGUI till)
        TillMenuView newMenu = null;
          //  newMenu = new TillMenuView(null, parent, true, tab);
        //    newMenu.addMouseListener(newMenu);
        //    newMenu.setTotal();
            newMenu.setEnabled(true);
            newMenu.setModal(true);
            Dialog d = (Dialog)newMenu;
            newMenu.selectorFrame = newMenu.makeBarTabSelector();
        return newMenu;
    } // makeMenu


    public BarTabDialogSelect getBarSelectorDialog()
    {
        return getSelectorFrame();
    }

    public HashMap<JButton, Pair<Integer, Table.TableStatus>> createJButtons(ArrayList<Table.TableStatus> tableStatuses) {
        HashMap<JButton, Pair<Integer, Table.TableStatus>> jb = new HashMap<>();
        for (int i = 1; i < tableStatuses.size(); i++)
            if (tableStatuses.get(i) != Table.TableStatus.FREE)
                  jb.put(new JButton("Table " + i), new Pair<>(i, tableStatuses.get(i)));

        return jb;
    } // createJButtons

    public void updateButtons(ArrayList<Table.TableStatus> tableStatuses)
    {
        System.out.println("update buttons");
        if (getSelectorFrame() == null)
            return;

                System.out.println("set Buttons");
        getSelectorFrame().setButtons(createJButtons(tableStatuses));
    }

    public BarTabDialogSelect makeBarTabSelector()
    {

        //TillClient c = (TillClient)parentClient;
        ArrayList<Table.TableStatus> tableStatuses = null;
//            (ArrayList<Table.TableStatus>) c.getTableStatuses().clone();

        HashMap<JButton, Pair<Integer, Table.TableStatus>> jbs = createJButtons(tableStatuses);
        BarTabDialogSelect newBTSelect = new BarTabDialogSelect((Dialog)this, true);
        newBTSelect.setButtons(jbs);
        return newBTSelect;
    }


	public BarTabDialogSelect getSelectorFrame() { return selectorFrame; }
	public void setSelectorFrame(BarTabDialogSelect selectorFrame) { this.selectorFrame = selectorFrame; }
} // class
