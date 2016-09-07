/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.client.frontend.output;

import com.mike.client.frontend.Client;
import com.mike.message.Request.RegisterClientRequest;
import static com.mike.message.Request.RegisterClientRequest.ClientType.BAR;
import static com.mike.message.Request.RegisterClientRequest.ClientType.KITCHEN;

/**
 *
 * @author mbbx9mg3
 */
public class OutputClient extends Client implements Runnable
{

     
    public OutputClient(RegisterClientRequest.ClientType type)  
    {
        super(type);
    } // constructor
    
    
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args)  throws InterruptedException
    {

        OutputClient client = null;
        try
        {
            switch(args[0])
            {
                case "bar": System.out.println("making bar"); 
                client = (OutputClient)Client.makeClient(BAR); 
                System.out.println("made bar " + client);break;
                case "kitchen": System.out.println("making kitchen"); 
                client = (OutputClient)Client.makeClient(KITCHEN); 
                System.out.println("made kitchen " + client); break;
                default: System.out.println("invalid argument"); System.exit(0); 
            }
            System.out.println("end of switch");

    //    client.debugGUI.setVisible(true);
        System.out.println("got here");
        while (true);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
           
    } // main


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


    
    
} // class

    

