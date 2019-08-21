/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsclient.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boban
 */
@Service
public class MessageService {
    
    private Channel channel;
    private Connection connection;
    private final String QUEUE_IDENT = "server";
    private final String HOST = "localhost";
    
    @PostConstruct
    public void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(HOST, false, false, false, null);       
    }
    
    public void sendMessage(String text) throws IOException {
        if (channel != null) {
            channel.basicPublish("", QUEUE_IDENT, null, text.getBytes());
            System.out.println(" [x] Sent message: '" + text + "'");
        }
    }
    
}
