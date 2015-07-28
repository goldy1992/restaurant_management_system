/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Message.Response;

import Server.Server;
import Message.Request.NumOfTablesRequest;

/**
 *
 * @author Goldy
 */
public class NumOfTablesResponse extends Response
{
    private int numOfTables;

    /**
     *
     * @param request
     */
    public NumOfTablesResponse(NumOfTablesRequest request)
    {
        super(request);

    } // contructor
    
    /**
     *
     * @return
     */
    public int getNumOfTables()
    {
        return numOfTables;
    } // getTableStatuses
    
    public void setNumberOfTables(int numOfTables)
    {
        this.numOfTables = numOfTables;
    }
        
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Number of Tables\ntotal: " + numOfTables; 

        x+= "\n";
        
        return x;
    }


}
