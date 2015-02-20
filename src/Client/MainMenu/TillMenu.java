/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Client.Client;
import Client.SelectTableMenu.SelectTable;
import Client.TillGUI;
import Item.Tab;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author mbbx9mg3
 */
public class TillMenu extends Menu 
{
    private final TillGUI till;
    
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
            newMenu.setVisible(true);

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
    
} // class
