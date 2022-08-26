package com.example.springboot.openMQ.produce;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Sender {
	public static void main(String[] args) {
		RetryProperties rPropertie = new RetryProperties();
		String destinationName=rPropertie.getMqDestinationName();
		destinationName="ProcessOrderQueue";
//		manualSend(destinationName,"140969730869232");
//		jmsTemplate(rPropertie);
		
		manualSendToPMS();
	}

	private static void manualSend(String destinationName,String content) {
		try {
			ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
			((com.sun.messaging.ConnectionFactory) cf)
					.setProperty(com.sun.messaging.ConnectionConfiguration.imqAddressList, "localhost:7676");
			Connection con = cf.createConnection();
			Session sn = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination dest = sn.createQueue(destinationName);
//			Destination dest = sn.createTopic(destinationName);
			MessageProducer mp = sn.createProducer(dest);
			TextMessage tm = sn.createTextMessage();
			tm.setStringProperty("AppLabel", "submit.OrderRegistration.Payment Manager");
			tm.setStringProperty("SalesChannel", "CITiii");
			tm.setText(content);
			mp.send(tm);
			System.out.println("Message sent:");
			sn.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void manualSendToPMS() {
		try {
			ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
			((com.sun.messaging.ConnectionFactory) cf)
					.setProperty(com.sun.messaging.ConnectionConfiguration.imqAddressList, "localhost:7676");
			Connection con = cf.createConnection();
			Session sn = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination dest = sn.createQueue("ProcessOrderQueue");
			MessageProducer mp = sn.createProducer(dest);
			TextMessage tm = sn.createTextMessage();
			tm.setStringProperty("AppLabel", "submit.OrderRegistration.Payment Manager");
			tm.setStringProperty("SalesChannel", "FRONT_DESK");
			tm.setText("140969729492927");
			mp.send(tm);
			System.out.println("Message sent:");
			sn.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}