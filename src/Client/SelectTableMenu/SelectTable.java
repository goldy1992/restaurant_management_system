
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client.SelectTableMenu;

import Client.MainMenu.Menu;
import Client.WaiterClient;
import static Message.Message.generateRequestID;
import Item.Tab;
import Message.EventNotification.TableStatusEvtNfn;
import Message.Request.LeaveRequest;
import Message.Request.Request;
import Message.Request.TabRequest;
import Message.Table;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Goldy
 */
public class SelectTable extends javax.swing.JFrame implements ActionListener 
{
    private final ObjectOutputStream out;
    private final JButton[] tableButtons;
    private final ArrayList<Table.TableStatus> tableStatuses;
    private int tableSelected = -1;
    public boolean tabReceived = false;
    private Tab tab;
    public final Object tabLock = new Object();
    public JButton openTable = new JButton("Open Table");
    public JButton moveTable = new JButton("Move Table");
    public Menu menu; // the menu GUI
    public final WaiterClient parentClient;
    
    /**
     * @return The table currently selected.
     */
    public int getTableSelected()
    {
        return tableSelected;
    }
    
    /**
     *
     * @param i the number of the table you want to get the status of.
     * @return The status of table "i"
     */
    public Table.TableStatus getTableStatus(int i)
    {
        if (i < 0 || i >= tableStatuses.size())
            return null;       
        else
            return tableStatuses.get(i);
    }
    
    /**
     * @return
     */
    public JButton[] getTableButtons()
    {
        return tableButtons;
    }
    
    /**
     * Mutator method to select the current table.
     * @param table the table to be selected.
     */
    public void setTableSelected(int table)
    {
        tableSelected = table;
    }
    
    /**
     *
     * @param index
     * @param t
     */
    public void setTableStatus(int index, Table.TableStatus t)
    {
        if (index < 0 || index >= tableButtons.length)
            return;    
        
        tableStatuses.set(index, t);
        switch(t)
        {
            case FREE:
                tableButtons[index].setBackground(Color.GREEN);
                tableButtons[index].setText("<html>Table " + index + "<br>Free</html>");
                break;
            case IN_USE:
                tableButtons[index].setBackground(Color.YELLOW);
                tableButtons[index].setText("<html>Table " + index+ "<br>Table in Use</html>");
                break;
            case OCCUPIED:
                tableButtons[index].setBackground(Color.RED);
                tableButtons[index].setText("<html>Table " + index + "<br>Occupied</html>");
                break;
        } // switch
    } // setTableStatus
    
    public void setTabReceived(boolean received)
    {
        this.tabReceived = received;
    }
    
    public void setTab(Tab tab)
    {
        this.tab = tab;
    }
    
    
      
    /**
     * Creates new form SelectTable
     * @param tableStatuses
     * @param out
     * @param parent
     */
    public SelectTable(ArrayList<Table.TableStatus> tableStatuses,
        ObjectOutputStream out, WaiterClient parent) 
    {
        this.tableStatuses = tableStatuses;
        this.out = out;
        this.parentClient = parent;
        parent.debugGUI.addText("size " + tableStatuses.size());
        tableButtons = new JButton[this.tableStatuses.size()];
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        FormPanel = new javax.swing.JPanel();
        FormOutputPanel = new javax.swing.JPanel();
        OutputLabel = new javax.swing.JLabel();
        ExternalOptionsPanel = new javax.swing.JPanel();
        TableNumPanel = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        MenuBarFile = new javax.swing.JMenu();
        MenuBarFileExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Select Table");
        setMinimumSize(new java.awt.Dimension(600, 650));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWeights = new double[] {0.25};
        jPanel3Layout.rowWeights = new double[] {0.25};
        FormPanel.setLayout(jPanel3Layout);

        FormOutputPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FormOutputPanel.setMinimumSize(new java.awt.Dimension(500, 100));
        FormOutputPanel.setPreferredSize(new java.awt.Dimension(500, 100));

        OutputLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout FormOutputPanelLayout = new javax.swing.GroupLayout(FormOutputPanel);
        FormOutputPanel.setLayout(FormOutputPanelLayout);
        FormOutputPanelLayout.setHorizontalGroup(
            FormOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OutputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
        );
        FormOutputPanelLayout.setVerticalGroup(
            FormOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OutputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.16666;
        FormPanel.add(FormOutputPanel, gridBagConstraints);

        ExternalOptionsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ExternalOptionsPanel.setMinimumSize(new java.awt.Dimension(100, 500));
        ExternalOptionsPanel.setPreferredSize(new java.awt.Dimension(100, 500));
        ExternalOptionsPanel.setLayout(new java.awt.GridLayout(6, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.8333;
        FormPanel.add(ExternalOptionsPanel, gridBagConstraints);
        ExternalOptionsPanel.add(openTable);
        openTable.addActionListener(this);
        ExternalOptionsPanel.add(moveTable);
        moveTable.addActionListener(this);

        TableNumPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableNumPanel.setMinimumSize(new java.awt.Dimension(400, 500));
        TableNumPanel.setPreferredSize(new java.awt.Dimension(400, 500));
        TableNumPanel.setLayout(new java.awt.GridLayout(6, 5));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        FormPanel.add(TableNumPanel, gridBagConstraints);
        for(int i = 1; i < tableButtons.length; i++)
        {
            tableButtons[i] = new JButton();
            TableNumPanel.add(tableButtons[i]);
            tableButtons[i].addActionListener(this);
            setTableStatus(i, tableStatuses.get(i));
        }

        MenuBarFile.setText("File");

        MenuBarFileExit.setText("Exit");
        MenuBarFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuBarFileExitActionPerformed(evt);
            }
        });
        MenuBarFile.add(MenuBarFileExit);

        MenuBar.add(MenuBarFile);

        jMenu2.setText("Edit");
        MenuBar.add(jMenu2);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FormPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FormPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentResized

    private void exitCode()
    {
        // TODO add your handling code here:
        int confirm = JOptionPane.showOptionDialog(null, 
            "Are You Sure to Close Application?", "Exit Confirmation", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
                   null, null, null);
                
                if (confirm == 0) 
                {
                    try 
                    {
                        LeaveRequest leaveRequest = new LeaveRequest(
                            InetAddress.getByName(
                                parentClient.client.
                                    getLocalAddress().getHostName()),
                            InetAddress.getByName(
                                parentClient.serverAddress.getHostName()),
                            generateRequestID(),
                            Request.RequestType.LEAVE);
                        out.writeObject(leaveRequest);
                    } // try 
                    catch (UnknownHostException ex) 
                    {
                        Logger.getLogger(SelectTable.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(SelectTable.class.getName()).log(Level.SEVERE, null, ex);
                    } // catch
                    
                } // if        
    }
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitCode();
    }//GEN-LAST:event_formWindowClosing

    private void MenuBarFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuBarFileExitActionPerformed
        // TODO add your handling code here:
                exitCode();
    }//GEN-LAST:event_MenuBarFileExitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ExternalOptionsPanel;
    private javax.swing.JPanel FormOutputPanel;
    private javax.swing.JPanel FormPanel;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu MenuBarFile;
    private javax.swing.JMenuItem MenuBarFileExit;
    private javax.swing.JLabel OutputLabel;
    private javax.swing.JPanel TableNumPanel;
    private javax.swing.JMenu jMenu2;
    // End of variables declaration//GEN-END:variables

    @Override
    @SuppressWarnings("empty-statement")
    public void actionPerformed(ActionEvent e) 
    {
        for (int i = 0; i < tableButtons.length; i++)
            if (e.getSource() == tableButtons[i])
            {
                OutputLabel.setText("<html><b>Would you like to open Table " + i + "?</b></html>");
                setTableSelected(i);
                            parentClient.debugGUI.addText("select table " +  getTableSelected());
            } // if

        
        if (e.getSource() == openTable)
        {
            if ( tableSelected == -1)
                OutputLabel.setText("You have no table selected yet!");
            else if (getTableStatus(tableSelected) == Table.TableStatus.IN_USE)
            {
                OutputLabel.setText("Table " + tableSelected + " in use");
            } // else if
            else
            {
                try 
                {
                    /* SEND A NOTIFICATION TO EVERYONE ELSE THAT TABLE IS NOW 
                       IN USE */
                    TableStatusEvtNfn newEvt = new TableStatusEvtNfn(InetAddress.getByName(parentClient.client.getLocalAddress().getHostName()),
                    InetAddress.getByName(parentClient.serverAddress.getHostName()),
                    generateRequestID(), tableSelected, Table.TableStatus.IN_USE);
                    out.reset();
                    out.writeObject(newEvt);
                    
                    /* Request the tab of this table from the server */
                    TabRequest tabStatusRequest = new TabRequest(InetAddress.getByName(parentClient.client.getLocalAddress().getHostName()),
                        InetAddress.getByName(parentClient.serverAddress.getHostName()),
                        generateRequestID(), 
                        Request.RequestType.TAB,
                            tableSelected);
                    out.reset();
                    out.writeObject(tabStatusRequest);
                   
                    synchronized(tabLock)
                    {
                        try
                        {
                            while(this.tabReceived == false)
                                tabLock.wait();
                        }
                        catch (InterruptedException ex) 
                        {
                            Logger.getLogger(SelectTable.class.getName()).log(Level.SEVERE, null, ex);
                        } // catch
                    } // synchronized
                    
                    menu = Menu.makeMenu(parentClient, this, tab, out);
                    //MyClient.debugGUI.addText("menu has been made");

                    this.tabReceived = false;
                


                    TableStatusEvtNfn newEvt1 = new TableStatusEvtNfn(InetAddress.getByName(parentClient.client.getLocalAddress().getHostName()),
                    InetAddress.getByName(parentClient.serverAddress.getHostName()),
                    generateRequestID(), tableSelected, Table.TableStatus.OCCUPIED);
                    out.writeObject(newEvt1);
                    OutputLabel.setText("");
                    tableSelected = -1;
                    
                
                } // try 
                catch (UnknownHostException ex) 
                {
                    Logger.getLogger(SelectTable.class.getName()).log(Level.SEVERE, null, ex);
                } // catch 
                catch (IOException ex) 
                {
                    Logger.getLogger(SelectTable.class.getName()).log(Level.SEVERE, null, ex);
                } // catch
            } // else
            
        }
        
        if(e.getSource() == moveTable)
        {
            
        } // if
        
    } // action Perfomed
    


}
