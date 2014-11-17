/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Server.Table;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author mbbx9mg3
 */
public class Tab implements Serializable  
{
    private final Table parent;
    private ArrayList<Item> items;
   
    
    public Tab(Table parent)
    {
        this.parent = parent;
        this.items = new ArrayList<Item>();
    } // tab
    
    
    
}
