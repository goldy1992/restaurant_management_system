package com.mike.client.MainMenu.Model;

public class MenuItem {
	


	private String name;
	private int id;
	private double price;
	private String type;
	private boolean needAgeCheck;
	private boolean stockCount;
	
    public MenuItem(String name, int id, 
            double price, String type, 
             boolean needAgeCheck, boolean stockCount) {
    	this.name = name;
    	this.id = id;
    	this.price = price;
    	this.needAgeCheck = needAgeCheck;
    	this.stockCount = stockCount;
    }
    
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}

	public boolean isNeedAgeCheck() {
		return needAgeCheck;
	}

	public boolean isStockCount() {
		return stockCount;
	}


}
