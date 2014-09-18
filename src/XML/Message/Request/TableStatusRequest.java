/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XML.Message.Request;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */
public class TableStatusRequest extends Request
{
    private ArrayList<Integer> tableList;
    
    public TableStatusRequest(InetAddress from, 
                              InetAddress to, 
                              String messageID, 
                              RequestType type,
                              ArrayList<Integer> tableList)
    {
        super(from, to, messageID, type);
        this.tableList = tableList;
    } // constructor
    
    public ArrayList<Integer> getTableList()
    {
        return tableList;
    }
    
}
