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

           
    } // main


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


    
    
} // class

    

