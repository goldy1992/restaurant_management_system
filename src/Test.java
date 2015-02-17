
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
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
        
        Executor myThreadPool = Executors.newFixedThreadPool(4);
        // creates the server
        Thread serverThread = new Thread(){
        @Override
        public void run()
        {
            Server.MyServer.main(null);
            
        }// run
        };
        myThreadPool.execute(serverThread);
        
        try { Thread.sleep(2000); } 
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
                Client.WaiterClient.main(null);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }// run
        };
        myThreadPool.execute(client);
        
        try { Thread.sleep(2000); } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        
        // creates the kitchen client
        Thread kitchenClient = new Thread(){
        @Override
        public void run()
        {
            String [] array = {"kitchen"};
            Client.OutputClient.main(array);
            
        }// run
        };
        myThreadPool.execute(kitchenClient);
        
                try {  Thread.sleep(2000); } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                // creates the bar client
        Thread barClient = new Thread(){
        @Override
        public void run()
        {
            String [] array = {"bar"};
            Client.OutputClient.main(array);
            
        }// run
        };
        myThreadPool.execute(barClient); 

             
    } // main   
} // class
