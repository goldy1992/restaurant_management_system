/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import com.mike.message.Request.*;
import com.mike.message.Request.databaseRequest.Query;
import com.mike.message.Request.databaseRequest.Update;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

/**
 *
 * @author Mike
 */
@MessageEndpoint
public class MessageRequestRouter {

    final static Logger logger = Logger.getLogger(MessageRequestRouter.class);

    @Router(inputChannel="messageRequestChannel")
    public String accept(Request request){
        logger.info("reached message request router");
        if (request instanceof RegisterClientRequest) {
			logger.info("return registerclient request router");
        	return "messageRegisterClientRequestChannel";
        } else if (request instanceof TableStatusRequest) {
        	return "messageTableStatusRequestChannel";
        } else if (request instanceof TabRequest) {
        	return "messageTabRequestChannel";
        } else if (request instanceof Query) {
        	return "messageQueryChannel";
        } else if (request instanceof Update) {
			return "messageUpdateChannel";
		} else if (request instanceof LeaveRequest) {
            return "messageLeaveRequestChannel";
        }
        return "messageRequestChannel";}
}

