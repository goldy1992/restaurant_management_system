/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goldy1992.rms.server;

import com.github.goldy1992.rms.message.EventNotification.EventNotification;
import com.github.goldy1992.rms.message.EventNotification.TabUpdateNfn;
import com.github.goldy1992.rms.message.EventNotification.TableStatusEvtNfn;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

/**
 *
 * @author Mike
 */
@MessageEndpoint
public class MessageEventNotificationRouter {

    final static Logger logger = Logger.getLogger(MessageEventNotificationRouter.class);
    
    @Router(inputChannel = "messageEventNotificationChannel")
    public String accept(EventNotification eventNotification){
        logger.debug("reached event notification router");
        if (eventNotification instanceof TableStatusEvtNfn) {
            return "messagetableStatusEventNotificationChannel";
        } else if (eventNotification instanceof TabUpdateNfn) {
			return  "messagetabUpdateNotificationChannel";
		}
        return null;
    }
}

