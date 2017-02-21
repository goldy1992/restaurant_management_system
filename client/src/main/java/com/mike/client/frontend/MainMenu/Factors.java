package com.mike.client.frontend.MainMenu;

import com.mike.client.backend.MessageFilter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author mbbx9mg3
 */
public class Factors {

    final static Logger logger = Logger.getLogger(Factors.class);
    
    /**
     *
     * @param n
     * @return
     */
    public static int[] closestIntSquare(int n) {
        TreeSet<Integer> factors = new TreeSet<Integer>();
        factors.add(n);
        factors.add(1);
        for(int test = n - 1; test >= Math.sqrt(n); test--) {
            if (n % test == 0) {
                factors.add(test);
                factors.add(n / test);
            }
        }
        ArrayList<Integer> s = new ArrayList<Integer>(factors);

        int[] arrayToReturn = new int[2];
   
        arrayToReturn[0] = s.get( (s.size() / 2) );
        
        if (s.size() % 2 == 0)
             arrayToReturn[1] = s.get( (s.size() / 2) - 1);
        else
            arrayToReturn[1] = s.get( (s.size() / 2) );

        return arrayToReturn;
        }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] x = closestIntSquare(114);
        
        for (int t: x)
        logger.info(t);
    }
}
