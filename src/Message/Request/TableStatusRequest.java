/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Message.Request;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */
public class TableStatusRequest extends Request
{
    private final ArrayList<Integer> tableList;
    
    
    public TableStatusRequest(InetAddress from, 
                              InetAddress to, 
                              String messageID, 
                              RequestType type,
                              ArrayList<Integer> tableList)
    {
        super(from, to, messageID, type);
        this.tableList = tableList;
    } // constructor
    
    @Override
    public ArrayList<Integer> getTableList()
    {
        return tableList;
    }
    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Table status\nTABLES: "; 
         for (int i = 0; i < tableList.size(); i++)
            x += (tableList.get(i) + " ");
        x+= "\n";
        
        return x;
    }
    
    
}