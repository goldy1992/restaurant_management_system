/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Response;

import Client.MyClient;

import Item.Tab;
import Message.Request.Request;
import Message.Request.TabRequest;
import Server.MyServer;

/**
 *
 * @author mbbx9mg3
 */
public class TabResponse extends Response
{
    private Tab currentTab;
    
    /**
     *
     * @param request
     */
    public TabResponse(Request request)
    {
        super(request);

    } // contructor
    
    /**
     *
     */
    @Override
    public void parse()
    {
       TabRequest request = (TabRequest)this.getRequest();
       int number  = request.getTabNumber();
       
       currentTab = MyServer.getTable(number).getCurrentTab();
    }
    
    public Tab getTab()
    {
        return currentTab;
    }

    @Override
    public void onReceiving() 
    {
        System.out.println("Executing TabResponse's onreceiving");
       synchronized(MyClient.selectTable.tabLock)
        {
              MyClient.selectTable.setTab(currentTab);
        MyClient.selectTable.setTabReceived(true);      
            MyClient.selectTable.tabLock.notifyAll();
        } 

        System.out.println("tab received: " + MyClient.selectTable.tabReceived);
    }
    
}
