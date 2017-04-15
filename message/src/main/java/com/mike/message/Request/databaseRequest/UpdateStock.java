package com.mike.message.Request.databaseRequest;

import com.mike.message.Request.Request;

import java.util.Map;

/**
 * Created by Mike on 15/04/2017.
 */
public class UpdateStock extends Request {

    private Map<Integer, Integer> itemQuantityMap;

    public UpdateStock(Map<Integer, Integer> itemQuantityMap) {
        this.itemQuantityMap = itemQuantityMap;
    }

    public Map<Integer, Integer> getItemQuantityMap() {
        return itemQuantityMap;
    }
} // class
