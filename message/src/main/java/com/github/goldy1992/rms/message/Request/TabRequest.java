/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goldy1992.rms.message.Request;

/**
 *
 * @author mbbx9mg3
 */
public class TabRequest extends Request {
    
   private final int tabNumber;

    /**
     * @param tableNumber
     */
    public TabRequest(int tableNumber) {
        super();
        this.tabNumber = tableNumber;
    } // constructor

    public String toString() {
        String x = super.toString() + "SUBTYPE: Tab Request";
        x+= "\nTab Number: " + tabNumber + "\n";
        return x;
    }   
    
    public int getTabNumber() { return tabNumber; }
} // class
