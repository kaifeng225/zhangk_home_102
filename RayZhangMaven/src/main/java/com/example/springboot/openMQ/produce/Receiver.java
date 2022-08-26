package com.example.springboot.openMQ.produce;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Receiver {
	public static void main(String[] args) {
		RetryProperties rPropertie = new RetryProperties();
		String destinationName=rPropertie.getMqDestinationName();
		destinationName="ProcessOrderQueue";
		syncReceiver(destinationName);
//		asynReceiver(rPropertie.getMqDestinationName());
	}

	private static void syncReceiver(String destinationName) {
		try {
			ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
			((com.sun.messaging.ConnectionFactory) cf)
					.setProperty(com.sun.messaging.ConnectionConfiguration.imqAddressList, "localhost:7676");
			Connection con = cf.createConnection();
			Session sn = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//topic的信息不能用Queue接受反过来也一样
			//topic只能订阅启动receiver之后的信息
			Destination dest = sn.createQueue(destinationName);
//			Destination dest = sn.createTopic(destinationName);
			MessageConsumer mc = sn.createConsumer(dest);
			//不用selector的时候可以接受到任何信息
//			MessageConsumer mc = sn.createConsumer(dest,"AppLabel = 'submit.OrderRegistration.Payment Manager' AND SalesChannel<>'POS-Offline'");
			con.start();
			TextMessage msg = (TextMessage) mc.receive();
			System.out.println("Received message: " + msg.getText());
			sn.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void asynReceiver(String destinationName) {
		try {
			ConnectionFactory conFactory = new com.sun.messaging.ConnectionFactory();
			((com.sun.messaging.ConnectionFactory) conFactory)
					.setProperty(com.sun.messaging.ConnectionConfiguration.imqAddressList, "localhost:7676");
			Connection con = conFactory.createConnection();
			Session sn = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination dest = sn.createQueue(destinationName);
//			Destination dest = sn.createTopic(destinationName);
			MessageConsumer mc = sn.createConsumer(dest);
			con.start();
			mc.setMessageListener(new AListener());
			System.out.println("Continuing its own work1");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}

class AListener implements MessageListener {
	public void onMessage(Message msg) {
		TextMessage tm = (TextMessage) msg;
		try {
			System.out.println("Received: " + tm.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}