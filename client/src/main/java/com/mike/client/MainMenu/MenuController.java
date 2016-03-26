package com.mike.client.MainMenu;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.mike.client.MessageSender;
import com.mike.client.MainMenu.Model.MenuModel;
import com.mike.client.SelectTableMenu.View.SelectTableView;
import com.mike.item.Tab;

public class MenuController {
	 
    public Menu view;
    public MenuModel model;
   
    
    @Autowired
    public MessageSender messageSender;
    
	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	
	public void init(SelectTableView tableView, Tab tab) throws SQLException {
		 // initialise the connection to the database
     
		this.model = new MenuModel();
		this.model.initialise();
		this.view = new Menu(tableView, model, true, tab);
		
	}
	


}
