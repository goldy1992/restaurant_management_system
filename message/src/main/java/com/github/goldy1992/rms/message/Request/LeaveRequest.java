package com.github.goldy1992.rms.message.Request;

/**
 * @author mbbx9mg3
 */
public class LeaveRequest extends Request {

    public LeaveRequest() {
        super();
    } // constructor

    public String toString() {
        String x = super.toString() + "SUBTYPE: Leave";
        x+= "\n";
        return x;
    } // toString
} // class
