/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message.Response;

import java.io.Serializable;

/**
 *
 * @author mbbx9mg3
 */
public interface DoWhenReceived extends Serializable 
{
    public void onReceiving();
}
