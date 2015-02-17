/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Item.Item;
import Message.EventNotification.NewItemNfn;

/**
 *
 * @author mbbx9mg3
 */
public class OutputGUI extends javax.swing.JFrame {

    private final Client parent;
    /**
     * Creates new form KitchenGUI
     * @param parent
     */
    public OutputGUI(Client parent) 
    {
        this.parent = parent;
        initComponents();

    }
   
    public void addText(String msg)
    {
        String currentText = outputPanel.getText();       
        currentText += msg;                     
        currentText += "\n\n";
        
        outputPanel.setText(currentText);
    }
    
    public void addMessage(NewItemNfn msg)
    {
        String currentText = outputPanel.getText();
        currentText += "Table " +  msg.getTable().getTableNumber() + " - " + parent.timeToString(msg.getHours(), msg.getMinutes()) + "\n";
        
        for (Item i : msg.getItems())
        {
            currentText += i.getQuantity() + "\t" + i.getName() + "\n";
            
            if (!i.getMessage().equals(""))
                currentText += i.getMessage() + "\n";
        } // for              
        currentText += "\n\n";
        
        outputPanel.setText(currentText);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        outputPanel = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(outputPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane outputPanel;
    // End of variables declaration//GEN-END:variables

    public static OutputGUI makeGUI(Client parent)
    {
        OutputGUI till = new OutputGUI(parent);
        // if for each title
        if (parent.getClass() == TillClient.class)
            till.setTitle("Till Client Output");
        else if (parent.getClass() == WaiterClient.class)
            till.setTitle("Waiter Client Output");

        till.setVisible(true);
        till.addText("pre while");
        return till;
    }
    
}
