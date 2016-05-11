package com.kk.poc.jms.queue.withoutspring;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class HelloWorldConsumer {

	public static void main(String[] args) {
		try {
			//Create a ConncetionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			//Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();
			//Create a session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//Create the destination (Topic or Queue)
			Destination destination = session.createQueue("HELLOWORLD.TESTQ");
			//Create a MessageProducer from the Session to the Topic or Queue
			MessageConsumer consumer = session.createConsumer(destination);
			Message message = consumer.receive(1000);
			if(message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				System.out.println("Received : " + text);
			}
			else {
				System.out.println("Received : " + message);
			}
			consumer.close();
			session.close();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace(); 
		}
	}

}
