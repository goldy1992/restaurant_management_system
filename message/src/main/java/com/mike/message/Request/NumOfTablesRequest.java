package com.mike.message.Request;

/**
 *
 * @author Goldy
 */
public class NumOfTablesRequest extends Request {

    public NumOfTablesRequest()
    {
        super();
    } // constructor

    public String toString() {
        String x = super.toString() + "SUBTYPE: number of Tables";
        x+= "\n";
        return x;
    }
} // class
