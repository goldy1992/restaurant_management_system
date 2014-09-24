/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XML.Message.Response;

import XML.Message.Request.TableStatusRequest;

/**
 *
 * @author Goldy
 */
public class TableStatusResponse extends ResponseMessage
{
    public TableStatusResponse(TableStatusRequest request)
    {
        super(request);
    }
    
    @Override
    public void parse()
    {
        if(gotResponse)
            return;
    }
}
