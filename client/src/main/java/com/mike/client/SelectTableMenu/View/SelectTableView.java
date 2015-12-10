package com.mike.client.SelectTableMenu.View;

import com.mike.client.SelectTableMenu.SelectTableController;
import com.mike.message.Table.TableStatus;
import static com.mike.message.Table.TableStatus.FREE;
import static com.mike.message.Table.TableStatus.IN_USE;
import static com.mike.message.Table.TableStatus.OCCUPIED;
import java.awt.Color;
import static java.awt.Color.BLACK;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * @author Goldy
 */
public class SelectTableView extends javax.swing.JFrame
{
    private final SelectTableController controller;
    private final JButton[] tableButtons;
    public JButton openTable = new JButton("Open Table");
    public JButton moveTable = new JButton("Move Table");
    public JButton cleanTable = new JButton("Clean Table");    
    private OutputLabel outputLabel;
    
    /**
     * Creates new form SelectTable
     * @param controller
     * @param statuses
     */
    public SelectTableView(SelectTableController controller, ArrayList<TableStatus> statuses) 
    {
        this.controller = controller;
        initComponents();
        tableButtons = makeTableButtons(statuses);
    } 
    
    /**
     * @return
     */
    public JButton[] getTableButtons()
    {
        return tableButtons;
    }
    
    /**
     *
     * @param index
     * @param t
     */
    public void setTableStatus(int index, TableStatus t)
    {
        switch(t)
        {
            case FREE:
                setButton(index, GREEN, getMessage(index, t));
                break;
            case IN_USE:
                setButton(index, YELLOW, getMessage(index, t));
                break;
            case OCCUPIED:
                setButton(index, RED, getMessage(index, t));
                break;
            // dirty default
            default:
                setButton(index, BLACK, getMessage(index, t) );
                break;
        } // switch
    } // setTableStatus
    
    private void setButton(int index, Color c, String msg)
    {
        tableButtons[index].setBackground(c);
        tableButtons[index].setText(msg);        
    }
    
    private String getMessage(int index, TableStatus t)
    {
        switch(t)
        {
            case FREE: return "<html>Table " + index + "<br>Free</html>";
            case IN_USE: return"<html>Table " + index + "<br>Table in Use</html>";
            case OCCUPIED: return "<html>Table " + index + "<br>Occupied</html>";
            default: return "<html><font color=white>Table " + index + "<br>Dirty</font></html>" ;
        } // switch        
    }
    
    private JButton[] makeTableButtons(ArrayList<TableStatus> statuses)
    {
        JButton[] buttons = new JButton[statuses.size()]; 
        for(int i = 1; i < statuses.size(); i++)
        {
            buttons[i] = new JButton();
            setTableStatus(i, statuses.get(i));
            TableNumPanel.add(tableButtons[i]); 
            buttons[i].addActionListener(controller);
        } // for
        
        return buttons;
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
        ExternalOptionsPanel = new javax.swing.JPanel();
        TableNumPanel = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        MenuBarFile = new javax.swing.JMenu();
        MenuBarFileExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Select Table");
        setMinimumSize(new java.awt.Dimension(600, 650));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWeights = new double[] {0.25};
        jPanel3Layout.rowWeights = new double[] {0.25};
        FormPanel.setLayout(jPanel3Layout);

        FormOutputPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FormOutputPanel.setMinimumSize(new java.awt.Dimension(500, 100));
        FormOutputPanel.setPreferredSize(new java.awt.Dimension(500, 100));

        javax.swing.GroupLayout FormOutputPanelLayout = new javax.swing.GroupLayout(FormOutputPanel);
        FormOutputPanel.setLayout(FormOutputPanelLayout);
        FormOutputPanelLayout.setHorizontalGroup(
            FormOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );
        FormOutputPanelLayout.setVerticalGroup(
            FormOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.16666;
        FormPanel.add(FormOutputPanel, gridBagConstraints);
        outputLabel = new OutputLabel();

        outputLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        FormPanel.add(outputLabel);

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
        openTable.addActionListener(controller);
        ExternalOptionsPanel.add(moveTable);
        moveTable.addActionListener(controller);
        ExternalOptionsPanel.add(cleanTable);
        cleanTable.addActionListener(controller);

        TableNumPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableNumPanel.setMinimumSize(new java.awt.Dimension(400, 500));
        TableNumPanel.setPreferredSize(new java.awt.Dimension(400, 500));
        TableNumPanel.setLayout(new java.awt.GridLayout(6, 5));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        FormPanel.add(TableNumPanel, gridBagConstraints);

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
                  //  controller.parentClient.leaveRequest();
                } // if        
    } // exitCode
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
    private javax.swing.JPanel TableNumPanel;
    private javax.swing.JMenu jMenu2;
    // End of variables declaration//GEN-END:variables

    public OutputLabel getOutputLabel()
    {
        return outputLabel;
    }
       
} // class
