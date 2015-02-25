/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Client.Client;
import Client.SelectTableMenu.SelectTable;
import Client.TillClient;
import Client.TillGUI;
import Item.Tab;
import Message.Table;
import java.awt.Dialog;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author mbbx9mg3
 */
public class TillMenu extends Menu 
{
    private final TillGUI till;
    public BarTabDialogSelect selectorFrame;
    public boolean tabLoaded = false;

    
    @Override
    protected String[] getOptionNames() { 
       String[] x = {"Print Bill", "Print Last Receipt", "Void", 
           "Void Last Item",  "Split Bill", "Order On Hold", "Delivered", 
           "Bar Tab", "Other Payment Methods", "Debit Card Pay", "Send Order"};
       return x;
    }
    public TillMenu(Client client, java.awt.Frame parent, boolean modal, 
        Tab tab, ObjectOutputStream stream, TillGUI parentGUI) throws SQLException
    {
        super(client, parent, modal, tab, stream);
        this.till = parentGUI;
    }
    
    @Override
    public void dealWithButtons(Object source) 
    {
        
        super.dealWithButtons(source);
         JButton button = (JButton)source;
        if (button.getText().equals("Bar Tab"))
        {
            if (!tabLoaded)
            {
                if (selectorFrame.numberOfTabs <= 0)
                    this.quantityTextPane.setText("there are no tabs to display!");
                else
                    selectorFrame.setVisible(true);
            } // if
        } // bar tab
        
    }
    
    public static TillMenu makeMenu(Client cParent, JFrame parent, 
        Tab tab, ObjectOutputStream out, TillGUI till)
    {
        
        TillMenu newMenu = null;
        try 
        {
            newMenu = new TillMenu(cParent, parent, true, tab, out, till);
            newMenu.addMouseListener(newMenu);
            newMenu.setTotal();
            newMenu.setEnabled(true);
            newMenu.setModal(true);
            Dialog d = (Dialog)newMenu;
            newMenu.selectorFrame = newMenu.makeBarTabSelector();

        } // try
        catch (SQLException ex) 
        {
            Logger.getLogger(SelectTable.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
        
        return newMenu;
    } // makeMenu
    
    public TillGUI getTill()
    {
        return till;
    }
    
    public BarTabDialogSelect getBarSelectorDialog()
    {
        return selectorFrame;
    }
    
    public HashMap<JButton, Integer> createJButtons(ArrayList<Table.TableStatus> tableStatuses)
    {
        HashMap<JButton, Integer> jb = new HashMap<>();
        for (int i = 1; i < tableStatuses.size(); i++)
            if (tableStatuses.get(i) != Table.TableStatus.FREE)
                  jb.put(new JButton("Table " + i), i);
        
        return jb;  
    } // createJButtons
    
    public void updateButtons(ArrayList<Table.TableStatus> tableStatuses)
    {
        System.out.println("update buttons");
        if (selectorFrame == null)
            return;
     
                System.out.println("set Buttons");
        selectorFrame.setButtons(createJButtons(tableStatuses));
    }
    

    
    public BarTabDialogSelect makeBarTabSelector()
    {

        TillClient c = (TillClient)parentClient;
        ArrayList<Table.TableStatus> tableStatuses = 
            (ArrayList<Table.TableStatus>) c.getTableStatuses().clone();
        
        HashMap<JButton, Integer> jbs = createJButtons(tableStatuses);
        BarTabDialogSelect newBTSelect = new BarTabDialogSelect((Dialog)this, true);
        newBTSelect.setButtons(jbs);
        return newBTSelect;
    }
    

} // class
