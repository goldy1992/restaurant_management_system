/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MainMenu;

import Client.Pair;
import Client.TillClient;
import Message.EventNotification.TableStatusEvtNfn;
import static Message.Message.generateRequestID;
import Message.Request.Request;
import Message.Request.TabRequest;
import Message.Table;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author mbbx9mg3
 */
public class BarTabDialogSelect extends javax.swing.JDialog {

    public static enum Functionality {GET_TAB, ADD_TO_TAB};
    
    public Functionality func = Functionality.GET_TAB;
    public int numberOfTabs;
    private boolean tabReceived;
    public final Object lock = new Object();
    
    /**
     * Creates new form BarTabDialogSelect
     * @param parent
     * @param modal
     */
    public BarTabDialogSelect(Dialog parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();      
        this.tabReceived = false;
    }
    
    public void setButtons(HashMap<JButton, Pair<Integer, Table.TableStatus>> jBs)
    {
        System.out.println("set buttons: " + jBs.size() + " buttons");
        final BarTabDialogSelect parent = this;      
        if (func == Functionality.ADD_TO_TAB)
        {
        JButton newTabButton = new JButton("New Tab");
        newTabButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //EnterQuantityDialog = new EnterQuantityDialog(this, true);
                parent.dispose();
            }
        });;
        this.getContentPane().add(newTabButton);            
        }
        ValueComparator bvc =  new ValueComparator(jBs);
        TreeMap<JButton, Pair<Integer, Table.TableStatus>> sortedMap = new TreeMap<>(bvc);
        sortedMap.putAll(jBs);
        numberOfTabs = jBs.size();
        if (jBs.size() <= 0)
            return;

        this.getContentPane().removeAll();
      
        for (JButton jb : sortedMap.keySet())
        {
            final Pair<Integer, Table.TableStatus> pair = jBs.get(jb);
            jb.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    TillMenu menuParent = (TillMenu)parent.getParent();
                    TillClient parentClient = (TillClient)menuParent.parentClient;
                    try 
                    {
                        if (pair.getSecond() == Table.TableStatus.IN_USE)
                        {
                            JOptionPane.showMessageDialog(parent, "Tab " + pair.getFirst() + " is currently in use!" );
                            return;
                        } // if
                        
                        /* SEND A NOTIFICATION TO EVERYONE ELSE THAT TABLE IS NOW 
                        IN USE */
                        TableStatusEvtNfn newEvt = new TableStatusEvtNfn(InetAddress.getByName(parentClient.client.getLocalAddress().getHostName()),
                        InetAddress.getByName(parentClient.serverAddress.getHostName()),
                        generateRequestID(), pair.getFirst(), Table.TableStatus.IN_USE);
                        menuParent.out.reset();
                        menuParent.out.writeObject(newEvt);
                        
                        TabRequest req = new TabRequest(                
                            InetAddress.getByName(
                                parentClient.client.getLocalAddress().getHostName()),
                            InetAddress.getByName(
                                parentClient.serverAddress.getHostName()),
                              
                            Message.Message.generateRequestID(), 
                            Request.RequestType.TAB,
                              pair.getFirst());
                        parentClient.getOutputStream().writeObject(req);
                        
                        synchronized(lock)
                        {      
                            while(menuParent.selectorFrame.tabReceived == false)
                                lock.wait();
                        } // sync

                        if (parent.func == Functionality.GET_TAB)
                        {
                            menuParent.tabLoaded = true;
                            System.out.println("GET TAB: set tab loaded = true");
                        }
                        else if (parent.func == Functionality.ADD_TO_TAB)
                        {
                            System.out.println("ADD TAB: set tab loaded = false");
                            menuParent.tabLoaded = false;
                            menuParent.oldTab = menuParent.oldTab.mergeTabs(menuParent.newTab);
                            menuParent.sendOrder();
                            menuParent.outputTextPane.setText("");
                            menuParent.setUpTab(null);
                            parent.dispose();
                            menuParent.dispose();
                        } // else if
                    } 
                    catch (UnknownHostException ex) 
                    {
                        Logger.getLogger(BarTabDialogSelect.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (IOException | InterruptedException ex) 
                    {
                        Logger.getLogger(BarTabDialogSelect.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    parent.dispose();
                }
            }); // actionListener
            this.getContentPane().add(jb);         
        } // for
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener()
        {

                @Override
                public void actionPerformed(ActionEvent e) {
                    parent.dispose();
                }
        });;
        this.getContentPane().add(cancelButton);
        this.pack();
        this.doLayout();
        this.revalidate();
        this.repaint();
    }
    
    class ValueComparator implements Comparator<JButton> {

    Map<JButton, Pair<Integer, Table.TableStatus>> base;
    public ValueComparator(Map<JButton, Pair<Integer, Table.TableStatus>> base) {
        this.base = base;
    }

    @Override 
    public int compare(JButton a, JButton b) {
        if (base.get(a).getFirst() < base.get(b).getFirst()) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
    }
    
    public boolean getTabReceived()
    {
        return tabReceived;
    }
    
    public void setTabReceived(boolean tabRecevied)
    {
        this.tabReceived = tabRecevied;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bar Tabs");
        getContentPane().setLayout(new java.awt.GridLayout(0, 5));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
