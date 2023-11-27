package rabbitMQ;

import java.util.Hashtable;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Publisher {

	public static void main(String[] args) {
//		sendMessageDemo("Hello world");
//		sendHeaderPatternMessage("header test","email");
		/**
		 * CREATE: {"WorkflowId":"72027"}
		 * COPY: Constants.WORKFLOW_CHANGE_PAYLOAD_COPY
		 */
		sendWorkflowChangeMessage2PL(Constants.WORKFLOW_CHANGE_PAYLOAD_COPY,"COPY");
//		sendTransactionMessage2PL("24854");
//		String payload=Constants.INSTANCE_CHANGE_PAYLOAD_PROCESS;
//		sendInstanceChangeMessage2PL("start",payload);
		String payload="{\"v2\":{\"eventType\":\"CREATE_ORDER\",\"orderId\":140969893358026}}";
		/**
		 * ES order handle: PMTMGR.*
		 * normal order handle: PMTMGR.CITIZEN_UI; PMTMGR.FRONT_DESK;PMTMGR.IVR;PMTMGR.SMS
		 * POS： *.POS-Offline
		 */
		String routingKey="ray.POS-Offline";
//		sendOrderMessage(payload,routingKey);
	}
	
	private static void sendMessageDemo(String message) {
		Connection connection=null;
		Channel channel=null;
		try {
			//get a connection and mq channel
			connection=RabbitConnectionUtil.getConnection();
			//create a channel
			channel=connection.createChannel();
           // declare a queue
			channel.queueDeclare(Constants.QUEUE_NAME,false,false,false,null);
			//send message: String exchange, String routingKey, boolean mandatory, BasicProperties props, byte[] body
			channel.basicPublish("", Constants.QUEUE_NAME, null, message.getBytes());
			System.out.println("sent message='"+message+"' successful");
			//close channel and connection
			RabbitConnectionUtil.closeConnection(connection,channel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RabbitConnectionUtil.closeConnection(connection,channel);
		}
	}
	
	private static void sendHeaderPatternMessage(String message,String infoType) {
		Connection connection=null;
		Channel channel=null;
		try {
			connection=RabbitConnectionUtil.getConnection();
			//创建与交换机的通道,每个通道代表一个会话 
			channel=connection.createChannel();
			/**
			 * 声明交换机String exchange, BuiltinExchangeType type
			 * 参数名称:
			 * 1.交换机名称
			 * 2.交换机类型
			 * fanout:对应的工作模式:Publish/Subscribe
			 * topic:对应的工作模式:Topics
			 * direct:对应的工作模式:Routing
			 * headers:对应的工作模式:Header
			 */
            channel.exchangeDeclare(Constants.EXCHANGE_HEADER_INFORM, BuiltinExchangeType.HEADERS);
          
            /**
             * 声明队列String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
             * 参数名称:
             * 1.队列名称
             * 2.是否持久化mq重启后队列还在
             * 3.是否独占连接,队列只允许在该连接中访问,如果连接关闭后就会自动删除了,设置true可用于临时队列的创建
             * 4.自动删除,队列不在使用时就自动删除,如果将此参数和exclusive参数设置为true时,就可以实现临时队列
             * 5.参数,可以设置一个队列的扩展参数,比如可以设置队列存活时间
             */
			channel.queueDeclare(Constants.QUEUE_HEADER_SMS,false,false,false,null);
			channel.queueDeclare(Constants.QUEUE_HEADER_EMAIL,false,false,false,null);
			/**
			 * 进行交换机队列的绑定
			 * 参数:String queue, String exchange, String routingKey
			 *  1. 队列名称
			 *  2. 交换机名称
			 *  3. 路由key
			 */
            Map<String, Object> headers_email = new Hashtable<String, Object>();
            headers_email.put("inform_type", "email");
            Map<String, Object> headers_sms = new Hashtable<String, Object>();
            headers_sms.put("inform_type", "sms");
            channel.queueBind(Constants.QUEUE_HEADER_SMS, Constants.EXCHANGE_HEADER_INFORM, "", headers_sms);
            channel.queueBind(Constants.QUEUE_HEADER_EMAIL,Constants.EXCHANGE_HEADER_INFORM, "", headers_email);
         
            for (int i = 0; i < 10; i++) {
                String headerMessage = message + i;
                Map<String, Object> headers = new Hashtable<String, Object>();
                headers.put("inform_type", infoType);//匹配infoType通知消费者绑定的header
                AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
                properties.headers(headers);
                /**
                 * 向交换机发送消息String exchange, String routingKey, boolean mandatory, BasicProperties props, byte[] body
                 * ##注意##：会往交换机下的所有队列发送消息
                 * 参数名称:
                 * 1.交换机,如果不指定将会使用mq默认交换机
                 * 2.路由key,交换机根据路由key来将消息转发至指定的队列,如果使用默认的交换机,routingKey设置为队列名称
                 * 3.消息属性
                 * 4.消息内容
                 */
                channel.basicPublish(Constants.EXCHANGE_HEADER_INFORM, "", properties.build(), headerMessage.getBytes());
                System.out.println("Send to "+infoType+": " + headerMessage);
            }
			//close channel and connection
			RabbitConnectionUtil.closeConnection(connection,channel);
		} catch (Exception e) {
			e.printStackTrace();
			RabbitConnectionUtil.closeConnection(connection,channel);
		}
	}
	
	/**
	 * send messages to permitting service
	 * 
	 * @param message:  Upsert={"WorkflowId":""}; ARCHIVE={"workflowIds":[]};
	 *                  COPY={"newWorkflowIds":[]};
	 * @param eventType: CREATE, SAVE, ARCHIVE, UPDATENAMEDESC, COPY
	 */
	private static void sendWorkflowChangeMessage2PL(String message, String eventType) {
		Connection connection=null;
		Channel channel=null;
		try {
			connection=RabbitConnectionUtil.getGuestConnection();
			channel=connection.createChannel();
//			channel.exchangeDeclare(Constants.EXCHANGE_HEADER_PERMITTING, BuiltinExchangeType.HEADERS,true);
//			channel.exchangeDeclare("jf.workflow-service.template-changes.alternate.exchange", BuiltinExchangeType.HEADERS,true);
//			channel.queueDeclare(Constants.PL_WORKFLOW_QUEUE_NAME,true,false,false,null);
//			Map<String, Object> headers = new Hashtable<String, Object>();
//			headers.put("EventType", eventType);
//			channel.queueBind(Constants.PL_WORKFLOW_QUEUE_NAME, Constants.EXCHANGE_HEADER_PERMITTING, "", headers);
		    //set header info
			Map<String, Object> sendheaders = new Hashtable<String, Object>();
			sendheaders.put("ApplicationId", "2");
			sendheaders.put("x-match", "all");
			sendheaders.put("EventType", eventType);//匹配eventType通知消费者绑定的header  
            AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
            properties.headers(sendheaders);
			//send message
			channel.basicPublish(Constants.EXCHANGE_HEADER_PERMITTING, "", properties.build(), message.getBytes());
			System.out.println("sent message='"+message+"' successful");
			//close channel and connection
			RabbitConnectionUtil.closeConnection(connection,channel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RabbitConnectionUtil.closeConnection(connection,channel);
		}
	}
	
	/**
	 * mock transaction message from payment manager service
	 * @param message
	 */
	private static void sendTransactionMessage2PL(String message) {
		Connection connection=null;
		Channel channel=null;
		try {
			connection=RabbitConnectionUtil.getGuestConnection();
			channel=connection.createChannel();  
			channel.exchangeDeclare("payment-manager-service.transaction.headers.exchange", BuiltinExchangeType.HEADERS,true);
			channel.queueDeclare("permitting_service.transaction.queue",true,false,false,null);
			Map<String, Object> sendheaders = new Hashtable<String, Object>();
			sendheaders.put("SourceModule", "PL");
			sendheaders.put("TransactionType", "PAYMENT");
			channel.queueBind("permitting_service.transaction.queue", "payment-manager-service.transaction.headers.exchange", "", sendheaders);			
            AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
            properties.headers(sendheaders);
			channel.basicPublish("payment-manager-service.transaction.headers.exchange", "", properties.build(), message.getBytes());
			System.out.println("sent message='"+message+"' successful");
			//close channel and connection
			RabbitConnectionUtil.closeConnection(connection,channel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RabbitConnectionUtil.closeConnection(connection,channel);
		}
	}
	
	/**
	 * mock transaction message from JF
	 * @param message
	 */
	private static void sendInstanceChangeMessage2PL(String header, String payload) {
		Connection connection=null;
		Channel channel=null;
		try {
			connection=RabbitConnectionUtil.getGuestConnection();
			channel=connection.createChannel();  
			channel.exchangeDeclare("jf.workflow-service.instance-changes.headers.exchange", BuiltinExchangeType.HEADERS,true);
			channel.queueDeclare("permitting_service.instance.queue",true,false,false,null);
			Map<String, Object> sendheaders = new Hashtable<String, Object>();
			sendheaders.put("EventType", header);
			channel.queueBind("permitting_service.instance.queue", "jf.workflow-service.instance-changes.headers.exchange", "", sendheaders);			
            AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
            properties.headers(sendheaders);
			channel.basicPublish("jf.workflow-service.instance-changes.headers.exchange", "", properties.build(), payload.getBytes());
			System.out.println("sent message='"+payload+"' successful");
			//close channel and connection
			RabbitConnectionUtil.closeConnection(connection,channel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RabbitConnectionUtil.closeConnection(connection,channel);
		}
		
	}
	
	private static void sendOrderMessage(String payload,String routingKey) {
		Connection connection=null;
		Channel channel=null;
		try {
			connection=RabbitConnectionUtil.getGuestConnection();
			channel=connection.createChannel();  					
           
			channel.basicPublish("aw.order-service.order.topic", routingKey, null, payload.getBytes());
			System.out.println("sent message='"+payload+"' successful");
			//close channel and connection
			RabbitConnectionUtil.closeConnection(connection,channel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RabbitConnectionUtil.closeConnection(connection,channel);
		}
		
	}

}
