package com.mike.client.frontend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mbbx9mg3
 */
public class Pair<X,Y> {
    
    private X x;
    private Y y;
    
    public Pair(X x, Y y)
    {
        this.x = x;
        this.y = y;
    }
    
    public X getFirst()
    {
        return x;
    }

    public Y getSecond()
    {
        return y;
    }    
    
    public void setX(X x)
    {
        this.x = x;
    }
    
    public void setY(Y y)
    {
        this.y = y;
    }
}
