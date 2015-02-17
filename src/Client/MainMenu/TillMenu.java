/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Client.Client;
import Client.MainMenu.Menu;
import Item.Tab;
import java.awt.Component;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import javax.swing.JPanel;

/**
 *
 * @author mbbx9mg3
 */
public class TillMenu extends Menu {
    
    @Override
    protected String[] getOptionNames() { 
       String[] x = {"Print Bill", "Print Last Receipt", "Void", 
           "Void Last Item",  "Split Bill", "Order On Hold", "Delivered", 
           "Bar Tab", "Other Payment Methods", "Debit Card Pay", "Send Order"};
       return x;
    }
    public TillMenu(Client client, java.awt.Frame parent, boolean modal, Tab tab, ObjectOutputStream stream) throws SQLException
    {
        super(client, parent, modal, tab, stream);
    }
    

    
}
