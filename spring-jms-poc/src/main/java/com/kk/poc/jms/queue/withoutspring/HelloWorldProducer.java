package com.kk.poc.jms.queue.withoutspring;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class HelloWorldProducer {

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
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//Create a Message
			String text = "Hello World from " + Thread.currentThread().getName();
			TextMessage message = session.createTextMessage(text);
			System.out.println("Sent message " + message.hashCode() + " : " + Thread.currentThread().getName());
			producer.send(message);
			session.close();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}