/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XML.Message.Response;

import Server.MyServer;
import Server.Table;
import XML.Message.Request.TableStatusRequest;
import java.util.ArrayList;

/**
 *
 * @author Goldy
 */
public class TableStatusResponse extends ResponseMessage
{
    public TableStatusResponse(TableStatusRequest request)
    {
        super(request);
        tableStatuses = new ArrayList();
    } // contructor
    
    private ArrayList<Table.TableStatus> tableStatuses;
    
    public ArrayList<Table.TableStatus> getTableStatuses()
    {
        return tableStatuses;
    } // getTableStatuses
    
    @Override
    public void parse()
    {
        if(gotResponse)
            return;
        
        TableStatusRequest x = (TableStatusRequest)this.getRequest();
        for (int i = 0; i < x.getTableList().size(); i++)
            tableStatuses.add(MyServer.getTable(x.getTableList().get(i)).getTableStatus());
             
        gotResponse = true;
    }
}
