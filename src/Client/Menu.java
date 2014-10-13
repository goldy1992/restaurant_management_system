/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author mbbx9mg3
 */
public class Menu extends javax.swing.JDialog implements MouseListener, ActionListener{

    /**
     * Creates new form Menu
     */
    public Menu(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        jPanel2 = new javax.swing.JPanel();
        FormPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OutputArea = new javax.swing.JTextPane();
        CardPanel = new javax.swing.JPanel();
        InitialCard = new javax.swing.JPanel();
        MenuSelectPanel = new javax.swing.JPanel();
        FoodMenuButton = new javax.swing.JButton();
        SpiritLiqButton = new javax.swing.JButton();
        WinesButton = new javax.swing.JButton();
        MineralsMixersButton = new javax.swing.JButton();
        BottlesButton = new javax.swing.JButton();
        FoodMenuPanel = new javax.swing.JPanel();
        BillHandleFrame = new javax.swing.JPanel();
        SendOrderButton = new javax.swing.JButton();
        VoidButton = new javax.swing.JButton();
        VoidLastItemButton = new javax.swing.JButton();
        DeliveredButton = new javax.swing.JButton();
        FoodCard = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

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

        FormPanel.setPreferredSize(new java.awt.Dimension(400, 500));
        FormPanel.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 150));

        jScrollPane1.setViewportView(OutputArea);
        OutputArea.addMouseListener(this);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        FormPanel.add(jPanel1, gridBagConstraints);

        CardPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CardPanel.setPreferredSize(new java.awt.Dimension(400, 350));
        CardPanel.setLayout(new java.awt.CardLayout());

        InitialCard.setLayout(new java.awt.GridLayout(1, 0));

        MenuSelectPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MenuSelectPanel.setLayout(new java.awt.GridLayout(5, 2));

        FoodMenuButton.setText("Food Menu");
        MenuSelectPanel.add(FoodMenuButton);
        FoodMenuButton.getAccessibleContext().setAccessibleName("");
        FoodMenuButton.addActionListener(this);

        SpiritLiqButton.setText("Spirits/Liqueurs");
        MenuSelectPanel.add(SpiritLiqButton);
        SpiritLiqButton.addActionListener(this);

        WinesButton.setText("Wines");
        MenuSelectPanel.add(WinesButton);
        WinesButton.addActionListener(this);

        MineralsMixersButton.setText("Minerals/Mixers");
        MenuSelectPanel.add(MineralsMixersButton);
        MineralsMixersButton.addActionListener(this);

        BottlesButton.setText("Bottles");
        MenuSelectPanel.add(BottlesButton);
        BottlesButton.addActionListener(this);

        InitialCard.add(MenuSelectPanel);

        FoodMenuPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout FoodMenuPanelLayout = new javax.swing.GroupLayout(FoodMenuPanel);
        FoodMenuPanel.setLayout(FoodMenuPanelLayout);
        FoodMenuPanelLayout.setHorizontalGroup(
            FoodMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        FoodMenuPanelLayout.setVerticalGroup(
            FoodMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        InitialCard.add(FoodMenuPanel);

        BillHandleFrame.setBorder(new javax.swing.border.MatteBorder(null));
        BillHandleFrame.setLayout(new java.awt.GridLayout(10, 1));

        SendOrderButton.setText("Send Order");
        BillHandleFrame.add(SendOrderButton);
        SendOrderButton.addActionListener(this);

        VoidButton.setText("Void");
        BillHandleFrame.add(VoidButton);
        VoidButton.addActionListener(this);

        VoidLastItemButton.setText("Void Last Item");
        BillHandleFrame.add(VoidLastItemButton);
        VoidLastItemButton.addActionListener(this);

        DeliveredButton.setText("Delivered");
        BillHandleFrame.add(DeliveredButton);
        DeliveredButton.addActionListener(this);

        InitialCard.add(BillHandleFrame);

        CardPanel.add(InitialCard, "mainCard");

        javax.swing.GroupLayout FoodCardLayout = new javax.swing.GroupLayout(FoodCard);
        FoodCard.setLayout(FoodCardLayout);
        FoodCardLayout.setHorizontalGroup(
            FoodCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1254, Short.MAX_VALUE)
        );
        FoodCardLayout.setVerticalGroup(
            FoodCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 338, Short.MAX_VALUE)
        );

        CardPanel.add(FoodCard, "foodCard");
        FoodCard.addMouseListener(this);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.7;
        FormPanel.add(CardPanel, gridBagConstraints);

        jMenu1.setText("File");
        MenuBar.add(jMenu1);

        jMenu2.setText("Edit");
        MenuBar.add(jMenu2);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1256, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public String currentCard = "mainCard";
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Menu dialog = new Menu(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BillHandleFrame;
    private javax.swing.JButton BottlesButton;
    private javax.swing.JPanel CardPanel;
    private javax.swing.JButton DeliveredButton;
    private javax.swing.JPanel FoodCard;
    private javax.swing.JButton FoodMenuButton;
    private javax.swing.JPanel FoodMenuPanel;
    private javax.swing.JPanel FormPanel;
    private javax.swing.JPanel InitialCard;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JPanel MenuSelectPanel;
    private javax.swing.JButton MineralsMixersButton;
    private javax.swing.JTextPane OutputArea;
    private javax.swing.JButton SendOrderButton;
    private javax.swing.JButton SpiritLiqButton;
    private javax.swing.JButton VoidButton;
    private javax.swing.JButton VoidLastItemButton;
    private javax.swing.JButton WinesButton;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        System.out.println("click");
        if(e.getSource() == OutputArea || e.getSource() == FoodCard)
        {
                    System.out.println("outPutAreaout");
            switch(currentCard)
            {
                case "foodCard":
                    CardLayout x = (CardLayout)CardPanel.getLayout();
                    currentCard = "mainCard";
                    x.show(CardPanel, "mainCard");
                    break;
                default: break;
            }
            
        } // if
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == FoodMenuButton)
        {
            CardLayout cl = (CardLayout)(CardPanel.getLayout());
            cl.show(CardPanel, "foodCard");
            currentCard = "foodCard";
            
        } // if
        else if (e.getSource() == SendOrderButton)
        {
            
            this.dispose();
        }
        
    } // actionPerformed
}
