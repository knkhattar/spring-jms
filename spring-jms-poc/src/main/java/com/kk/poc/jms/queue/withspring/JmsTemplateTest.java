package com.kk.poc.jms.queue.withspring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JmsTemplateTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("JmsTemplate-context.xml");
		JmsTemplate template = (JmsTemplate) ctx.getBean("jmsTemplate");
//		for(int i=0; i<10; i++) {
			template.send("SpringSendTestQueue", new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					TextMessage tx = session.createTextMessage();
					tx.setText("**This ia a test message**");
					return tx;
				}
			});
			System.out.println("Message Sent");
//		}
		Message message = template.receive("SpringSendTestQueue");
		TextMessage textMessage = (TextMessage) message;
		  try {
			System.out.println("Message Received : "+ textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

}
