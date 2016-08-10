package com.mike.client.MainMenu;

import com.mike.client.MainMenu.Model.MenuModel;
import com.mike.client.MessageSender;
import com.mike.client.SelectTableMenu.View.SelectTableView;
import com.mike.item.Tab;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuController {
	 
    public Menu view;
    private MenuModel model;
   
    
    public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	@Autowired
    public MessageSender messageSender;
    
	public void setMessageSender(MessageSender messageSender) { this.messageSender = messageSender; }
	
	public void init(SelectTableView tableView, Tab tab) {
		 // initialise the connection to the database
     
		//this.model = new MenuModel();
		this.model.initialise();
		this.view = new Menu(tableView, model, true, tab);
		view.setVisible(true);
		
	}
	


}
