/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XML;

/**
 *
 * @author Goldy
 */
public class RequestException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public RequestException(String error_msg)
    {
        super(error_msg);
    }
    
    public RequestException(Exception e)
    {
        super(e);
    }
    
    
    
}
