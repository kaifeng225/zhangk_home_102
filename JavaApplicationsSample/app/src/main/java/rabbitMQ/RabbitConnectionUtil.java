package rabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitConnectionUtil {

	public static Connection getConnection() throws Exception{
		// declare connectionFactory
		ConnectionFactory factory=new ConnectionFactory();
		// set server address
		factory.setHost("localhost");
		// set port
		factory.setPort(5672);
		//set a virtual host, a virtual host is equal to an MQ
		factory.setVirtualHost("RayZhang");
		// set account info
		factory.setUsername("RayZhang");
		factory.setPassword("zhangk225");
		//get connection
		return factory.newConnection();
	}
	
	public static Connection getGuestConnection() throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setVirtualHost("/aw");
		factory.setUsername("guest");
		factory.setPassword("guest");
		return factory.newConnection();
	}
	public static void closeConnection(Connection conn,Channel channel) {
		if(channel!=null&&channel.isOpen()) {
			try {
				channel.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null&& conn.isOpen()) {
			try {
				conn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
