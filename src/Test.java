
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mbbx9mg3
 */
public class Test 
{
    public static void main(String[] args)
    {
        // creates the server
        Thread serverThread = new Thread(){
        @Override
        public void run()
        {
            Server.MyServer.main(null);
            
        }// run
        };
        serverThread.start();
        
        try
        {
            Thread.sleep(2000);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // creates the bar client
        Thread barClient = new Thread(){
        @Override
        public void run()
        {
            Bar.BarClient.main(null);
            
        }// run
        };
        barClient.start(); 
        
        // creates the kitchen client
        Thread kitchenClient = new Thread(){
        @Override
        public void run()
        {
            Kitchen.KitchenClient.main(null);
            
        }// run
        };
        kitchenClient.start(); 
           
        try
        {
            Thread.sleep(2000);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // creates the waiter client
        Thread client = new Thread(){
        @Override
        public void run()
        {
            try
            {
                Client.MyClient.main(null);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }// run
        };
        client.start();
             
    } // main   
} // class
