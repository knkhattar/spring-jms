package com.kk.poc.jms.topic.withoutspring;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
public class MySender {
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
			Destination destination = session.createTopic("KK.TESTTOPIC");
			//Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//Create a Message
			BufferedReader r = new BufferedReader( new FileReader( "testing.txt" ) );
			String text = "", line = null;
			while ((line = r.readLine()) != null) {
				text += line;
			}
//			String text = "Synchronization --> Hello World from " + Thread.currentThread().getName();
			TextMessage message = session.createTextMessage(text);
			System.out.println("Sent message " + message.hashCode() + " : " + Thread.currentThread().getName());
			producer.send(message);
			session.close();
			connection.close();
		} catch(Exception e) {
			System.out.println("Error Occured : " + e);
			e.printStackTrace();
		}
	}
}