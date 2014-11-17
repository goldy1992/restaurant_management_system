/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Message.Response;

import Client.MyClient;
import Server.MyServer;
import Message.Request.NumOfTablesRequest;

/**
 *
 * @author Goldy
 */
public class NumOfTablesResponse extends Response
{

    /**
     *
     * @param request
     */
    public NumOfTablesResponse(NumOfTablesRequest request)
    {
        super(request);

    } // contructor
    
    private int numOfTables;
    
    /**
     *
     * @return
     */
    public int getNumOfTables()
    {
        return numOfTables;
    } // getTableStatuses
    
    /**
     * This should only be able to be done server side
     */
    @Override
    public void parse()
    {
        if(parsedResponse)
            return;
        
        numOfTables = MyServer.getNumOfTables();
        // set response to true
        parsedResponse = true;
    }
    
    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Number of Tables\ntotal: " + numOfTables; 

        x+= "\n";
        
        return x;
    }

    @Override
    public void onReceiving() 
    {
        System.out.println(this);
        //System.out.println("got  num of tables: " + r.getNumOfTables());
        MyClient.setNumTables(this.getNumOfTables());
    }
}
