package Client.MainMenu;

import java.util.ArrayList;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mbbx9mg3
 */
public class Factors 
{

    /**
     *
     * @param n
     * @return
     */
    public static int[] closestIntSquare(int n)
    {
        TreeSet<Integer> factors = new TreeSet<Integer>();
        factors.add(n);
        factors.add(1);
        for(int test = n - 1; test >= Math.sqrt(n); test--)
            if(n % test == 0)
            {
                factors.add(test);
                factors.add(n / test);
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
    public static void main(String[] args)
    {
        int[] x = closestIntSquare(114);
        
        for (int t: x)
        System.out.println(t);
    }
}
