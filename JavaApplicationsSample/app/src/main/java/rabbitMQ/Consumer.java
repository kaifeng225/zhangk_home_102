package rabbitMQ;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Consumer {

    public static void main(String[] args) {
//		consumeMessageDemo();
        /**
         * "sms",Constants.QUEUE_HEADER_SMS
         *  "email",Constants.QUEUE_HEADER_EMAIL
         */
//		consumeHeaderMessage("email",Constants.QUEUE_HEADER_EMAIL);
        /**
         * CREATE, SAVE, ARCHIVE, UPDATENAMEDESC, COPY
         */
//		consumePermittingMessage("CREATE");
        consumeDeadLetter("permitting_service.workflow.dead-letter.queue");

    }

    private static void consumeMessageDemo() {
        Connection connection = null;
        Channel channel = null;
        try {
            //get connection and mq channel
            connection = RabbitConnectionUtil.getConnection();
            //create channel from connection
            channel = connection.createChannel();
            //declare a queue
            channel.queueDeclare(Constants.QUEUE_NAME, false, false, false, null);

            // declare consumer of the queue
            DefaultConsumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                  throws IOException {
                    System.out.println("receive message:" + new String(body, "UTF-8"));
                }

            };

            //monitor the queue
            //
            channel.basicConsume(Constants.QUEUE_NAME, true, consumer);
            System.out.println("start to monitor the queue");
            /**
             * hold the process to wait the message
             * close connection will not waiting for new messages
             */
//		RabbitConnectionUtil.closeConnection(connection,channel);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            RabbitConnectionUtil.closeConnection(connection, channel);
        }
    }

    private static void consumeHeaderMessage(String infoType, String queueName) {
        Connection connection = null;
        Channel channel = null;
        try {
            //建立新连接
            connection = RabbitConnectionUtil.getConnection();
            //创建会话通道,生产者和mq服务所有通信都在channel中完成
            channel = connection.createChannel();
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
             * 监听队列
             * 声明队列String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
             * 参数名称:
             * 1.队列名称
             * 2.是否持久化mq重启后队列还在
             * 3.是否独占连接,队列只允许在该连接中访问,如果连接关闭后就会自动删除了,设置true可用于临时队列的创建
             * 4.自动删除,队列不在使用时就自动删除,如果将此参数和exclusive参数设置为true时,就可以实现临时队列
             * 5.参数,可以设置一个队列的扩展参数,比如可以设置队列存活时间
             */
            channel.queueDeclare(queueName, false, false, false, null);
            /**
             * 进行交换机队列的绑定
             * 参数:String queue, String exchange, String routingKey
             *  1. 队列名称
             *  2. 交换机名称
             *  3. 路由key
             */
            Map<String, Object> headers = new Hashtable<String, Object>();
            headers.put("inform_type", infoType);
            channel.queueBind(queueName, Constants.EXCHANGE_HEADER_INFORM, "", headers);

            //实现消费方法
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                /**
                 * 接受到消息后此方法将被调用
                 * @param consumerTag  消费者标签 用来标记消费者的,可以不设置,在监听队列的时候设置
                 * @param envelope  信封,通过envelope可以获取到交换机,获取用来标识消息的ID,可以用于确认消息已接收
                 * @param properties 消息属性,
                 * @param body 消息内容
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    // 交换机
                    String exchange = envelope.getExchange();
                    // 路由key
                    String routingKey = envelope.getRoutingKey();
                    // 消息Id mq在channel中用来标识消息的id,可用于确认消息已接受
                    long deliveryTag = envelope.getDeliveryTag();
                    // 消息内容
                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.println("receive header " + infoType + " message: " + message);
                }
            };
            /**
             * 监听消息String queue, boolean autoAck, Map<String, Object> arguments, Consumer callback
             * 参数名称:
             * 1.队列名称
             * 2.自动回复, 当消费者接受消息之后要告诉mq消息已经接受,如果将此参数设置为true表示会自动回复mq,如果设置为false要通过编程去实现了
             * 3.callback 消费方法,当消费者接受到消息之后执行的方法
             */
            channel.basicConsume(queueName, true, defaultConsumer);
            RabbitConnectionUtil.closeConnection(connection, channel);
        } catch (Exception e) {
            e.printStackTrace();
            RabbitConnectionUtil.closeConnection(connection, channel);
        }
    }

    private static void consumePermittingMessage(String eventType) {
        Connection connection = null;
        Channel channel = null;
        try {
            //get connection and mq channel
            connection = RabbitConnectionUtil.getGuestConnection();
            //create channel from connection
            channel = connection.createChannel();
//		channel.exchangeDeclare(Constants.EXCHANGE_HEADER_PERMITTING, BuiltinExchangeType.HEADERS);

            //declare a queue
            channel.queueDeclare(Constants.PL_WORKFLOW_QUEUE_NAME, false, false, false, null);
            /**
             * no need to declare a exchange and bind it to the queue,because the publisher can do this
             */
//		Map<String, Object> headers = new Hashtable<String, Object>();
//		headers.put("EventType", eventType);
//		channel.queueBind(Constants.PL_WORKFLOW_QUEUE_NAME, Constants.EXCHANGE_HEADER_PERMITTING, "", headers);		

            // declare consumer of the queue
            DefaultConsumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                  throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);

                    MapUtils.emptyIfNull(properties.getHeaders()).forEach((key, value) -> {
                        System.out.println("received message header:key=" + key + ";value=" + value);
                    });
                    System.out.println("receive message:" + new String(body, "UTF-8"));
                }

            };

            //monitor the queue
            channel.basicConsume(Constants.PL_WORKFLOW_QUEUE_NAME, true, consumer);
            RabbitConnectionUtil.closeConnection(connection, channel);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            RabbitConnectionUtil.closeConnection(connection, channel);
        }

    }

    private static void consumeDeadLetter(String queuename) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitConnectionUtil.getGuestConnection();
            channel = connection.createChannel();
            Map<String, Object> arguments = Maps.newHashMap();
//		arguments.put("x-dead-letter-exchange", "permitting_service.workflow.dlx_exchange");
//		arguments.put("x-dead-letter-routing-key", "workflow_dead_letter_key");	
            arguments.put("x-queue-type", "quorum");
            channel.queueDeclare(queuename, true, false, false, arguments);
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                  throws IOException {
                    System.out.println("receive message:" + new String(body, "UTF-8"));
                }

            };

            channel.basicConsume(queuename, true, consumer);
            System.out.println("start to monitor the queue");
            /**
             * hold the process to wait the message
             * close connection will not waiting for new messages
             */
//		RabbitConnectionUtil.closeConnection(connection,channel);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            RabbitConnectionUtil.closeConnection(connection, channel);
        }
    }

}
