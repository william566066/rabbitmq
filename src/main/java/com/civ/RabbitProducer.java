package com.civ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by william on 2016/8/1.
 */
public class RabbitProducer {

    private final static String QUEUE_NAME = "test";
    private static final String EXCHANGE_NAME = "amq.topic";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("180.76.128.51");
        Connection connection = null;
        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //channel.exchangeDeclare(EXCHANGE_NAME, "topic",true);

            String message = "Hello World!";
            for (int i = 0; i < 10; i++) {
                message = message + i;
                channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, message.getBytes());
            }
            //channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
