/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Message.Response;

import Server.MyServer;
import Message.Table;
import Message.Request.TableStatusRequest;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */
public class TableStatusResponse extends Response
{
    
    private final ArrayList<Table.TableStatus> tableStatuses;

    /**
     *
     * @param request
     */
    public TableStatusResponse(TableStatusRequest request)
    {
        super(request);
        tableStatuses = new ArrayList<Table.TableStatus>();
    } // contructor
       
    /**
     *
     * @return
     */
    public ArrayList<Table.TableStatus> getTableStatuses()
    {
        return tableStatuses;
    } // getTableStatuses
    
    @Override
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Table status\nTABLES: "; 
         for (int i = 0; i < tableStatuses.size(); i++)
            x += (tableStatuses.get(i) + " ");
        x+= "\n";
        
        return x;
    } // toString

} // class
