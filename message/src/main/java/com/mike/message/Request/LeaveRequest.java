/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.message.Request;

/**
 *
 * @author mbbx9mg3
 */
public class LeaveRequest extends Request
{

    public LeaveRequest()
    {
        super();
    } // constructor

    public String toString()
    {
        String x = super.toString() + "SUBTYPE: Leave";
        x+= "\n";
        
        return x;
    }   


}
