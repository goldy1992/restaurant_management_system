/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.server;

import javax.jms.Queue;
 
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
 
public class DemoMain {
 
  public static void main(String[] args) {
    // init spring context
    ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
         
    // get bean from context
    JmsMessageSender jmsMessageSender = (JmsMessageSender)ctx.getBean("jmsMessageSender");
         
    // send to default destination 
    jmsMessageSender.send("hello JMS");
         
    // send to a code specified destination
    Queue queue = new ActiveMQQueue("AnotherDest");
    jmsMessageSender.send(queue, "hello Another Message");
   
    // close spring application context
    ((ClassPathXmlApplicationContext)ctx).close();
  }
 
}
