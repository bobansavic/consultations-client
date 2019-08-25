/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsclient.service;

import com.bs.consultationsclient.model.RabbitMqMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boban
 */
public class RabbitMqService {

    private static final String ACTION_CODE_0 = "ACTION_CODE_0";
    private static final String ACTION_CODE_1 = "ACTION_CODE_1";

    private Channel channel;
    private Connection connection;
    private Consumer consumer;
    private final String QUEUE_IDENT = "server";
    private final String PERSONAL_QUEUE_ID = "queue_1";
    private final String HOST = "localhost";

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(PERSONAL_QUEUE_ID, false, false, false, null);
        consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                /*ByteArrayInputStream bis = new ByteArrayInputStream(body);
                ObjectInput in = null;
                
                try {
                    in = new ObjectInputStream(bis);
                    RabbitMqMessage payload = (RabbitMqMessage) in.readObject();
                    
                    switch(payload.getActionCode()) {
                        case ACTION_CODE_0:
                            System.out.println("LOGIN FAILED!");
                            break;
                        case ACTION_CODE_1:
                            System.out.println("LOGIN SUCCESSFUL!");
                            break;
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RabbitMqService.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(RabbitMqService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }*/
//                String message = new String(body, "UTF-8");
//                System.out.println(" [x] Received message: '" + message + "'");
                ObjectMapper mapper = new ObjectMapper();

                try {
                    // JSON string to Java object
                    String jsonInString = new String(body, "UTF-8");
                    RabbitMqMessage payload = mapper.readValue(jsonInString, RabbitMqMessage.class);
                    
                    switch(payload.getActionCode()) {
                        case ACTION_CODE_0:
                            System.out.println("LOGIN FAILED!");
                            break;
                        case ACTION_CODE_1:
                            System.out.println("LOGIN SUCCESSFUL!");
                            break;
                    }

                    // compact print
                    System.out.println(payload);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_IDENT, true, consumer);
    }

    public void sendMessage(byte[] payload) throws IOException {
        if (channel != null) {
            channel.basicPublish("", QUEUE_IDENT, null, payload);
            System.out.println(" [x] RabbitMqMessage sent!");
        }
    }
}
