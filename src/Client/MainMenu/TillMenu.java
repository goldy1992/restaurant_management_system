/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Client.Pair;
import Client.Client;
import Client.SelectTableMenu.SelectTable;
import Client.TillClient;
import Client.TillGUI;
import Item.Tab;
import Message.EventNotification.TableStatusEvtNfn;
import static Message.Message.generateRequestID;
import Message.Table;
import java.awt.Dialog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    
    private void barTabPressed()
    {
        if (!tabLoaded) {
            System.out.println("tabLoaded == false");
            if (oldTab.getNumberOfItems() + newTab.getNumberOfItems() <= 0) {
                System.out.println("get tab selected");
                selectorFrame.setState(BarTabDialogSelect.Functionality.GET_TAB);
            } else {
                selectorFrame.setState(BarTabDialogSelect.Functionality.ADD_TO_TAB);
            }

            if (selectorFrame.getState() == BarTabDialogSelect.Functionality.GET_TAB && selectorFrame.numberOfTabs <= 0) {
                this.quantityTextPane.setText("there are no tabs to display!");
            } else {
                selectorFrame.setVisible(true);
            }
        } // if
        else 
        {
            this.oldTab = oldTab.mergeTabs(newTab);
            sendOrder();
            /* SEND A NOTIFICATION TO EVERYONE ELSE THAT TABLE IS NOW 
             Occupied */
            TableStatusEvtNfn newEvt;
            try {
                newEvt = new TableStatusEvtNfn(InetAddress.getByName(parentClient.client.getLocalAddress().getHostName()),
                        InetAddress.getByName(parentClient.serverAddress.getHostName()),
                        generateRequestID(), newTab.getTable().getTableNumber(), Table.TableStatus.OCCUPIED);

                out.reset();
                out.writeObject(newEvt);
            } catch (UnknownHostException ex) {
                Logger.getLogger(TillMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TillMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.setUpTab(null);
            this.outputTextPane.setText("");
            this.tabLoaded = false;
            this.dispose();     
        }
    }
    
    public void writeLastReceipt()
    {
        System.out.println("called last receipt");
        if (this.lastReceipt == null)
            return;
        try
        {
            System.out.println("last receipt not null");
            File file = new File("Last _Bill_Receipt_" + oldTab.getTable().getTableNumber() + ".txt");
            int i = 1;
            while (file.exists())
            {    
                file = new File("Last _Bill_Receipt_" + oldTab.getTable().getTableNumber() + "(" + i + ")" + ".txt");
                i++;
            } // while

            // creates the file
            file.createNewFile();
            // Writes the content to the file
            try ( FileWriter writer = new FileWriter(file) ) 
            {
                // Writes the content to the file
                writer.write(this.lastReceipt);
                writer.flush();
            } 
            sendOrder(); 
            
            this.dispose();

        }
        catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);}    
    } // writeBill
       
    @Override
    public void dealWithButtons(Object source) throws SQLException 
    {       
        super.dealWithButtons(source);
         JButton button = (JButton)source;
        
        switch (button.getText())
        {
            case "Bar Tab": barTabPressed(); break;
            case "Print Last Receipt": writeLastReceipt(); break;
            default: break;
        } // switch
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
    
    public HashMap<JButton, Pair<Integer, Table.TableStatus>> createJButtons(ArrayList<Table.TableStatus> tableStatuses)
    {
        HashMap<JButton, Pair<Integer, Table.TableStatus>> jb = new HashMap<>();
        for (int i = 1; i < tableStatuses.size(); i++)
            if (tableStatuses.get(i) != Table.TableStatus.FREE)
                  jb.put(new JButton("Table " + i), new Pair<>(i, tableStatuses.get(i)));
        
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
        
        HashMap<JButton, Pair<Integer, Table.TableStatus>> jbs = createJButtons(tableStatuses);
        BarTabDialogSelect newBTSelect = new BarTabDialogSelect((Dialog)this, true);
        newBTSelect.setButtons(jbs);
        return newBTSelect;
    }
    

} // class
