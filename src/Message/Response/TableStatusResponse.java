/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Message.Response;

import Server.MyServer;
import Server.Table;
import Message.Request.TableStatusRequest;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */
public class TableStatusResponse extends Response
{
    public TableStatusResponse(TableStatusRequest request)
    {
        super(request);
        tableStatuses = new ArrayList();
    } // contructor
    
    private final ArrayList<Table.TableStatus> tableStatuses;
    
    /**
     *
     * @return
     */
    public ArrayList<Table.TableStatus> getTableStatuses()
    {
        return tableStatuses;
    } // getTableStatuses
    
    @Override
    public void parse()
    {
        if(parsedResponse)
            return;
        
        // cast the request to a table request
        TableStatusRequest x = (TableStatusRequest)this.getRequest();
 
        //System.out.println("gettign statuses");
        // for each table in the request, add its status to the ArrayList
 
        for (int i = 0; i < x.getTableList().size(); i++)
        {
          //  System.out.println(i + " table number: " + x.getTableList().get(i));
            tableStatuses.add(MyServer.getTable(x.getTableList().get(i)).getTableStatus());
        }    
        // set response to true
        parsedResponse = true;
    }
    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Table status\nTABLES: "; 
         for (int i = 0; i < tableStatuses.size(); i++)
            x += (tableStatuses.get(i) + " ");
        x+= "\n";
        
        return x;
    }
}
