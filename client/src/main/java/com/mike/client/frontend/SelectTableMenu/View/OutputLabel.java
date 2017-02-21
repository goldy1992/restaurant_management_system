/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.frontend.SelectTableMenu.View;

import javax.swing.*;

/**
 *
 * @author Mike
 */
public class OutputLabel extends JLabel 
{
    public OutputLabel()
    {
        super();
    }
    
    public void setOutputLabelOpenQuery(int tableSelected)
    {
        setText("<html><b>Would you like to open Table " 
                + tableSelected + "?</b></html>");
    }
    
    public void setOutputLabelTableInUse(int tableSelected)
    {
        setText("Table " + tableSelected + " in use");
    }
    
    public void setOutputLabelNoTableSelected()
    {
        setText("You have no table selected yet!");        
    }
    
    public void resetOutputLabel()
    {
        setText("");
    }

    
}
