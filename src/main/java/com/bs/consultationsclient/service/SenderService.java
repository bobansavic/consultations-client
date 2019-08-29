package com.bs.consultationsclient.service;

import com.bs.consultationsclient.model.RabbitMqMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class SenderService {
    private static final String SERVER_QUEUE = "server";
    private static final String ACTION_CODE_1 = "ACTION_CODE_1";

  @Autowired
  private RabbitTemplate rabbitTemplate;
  
  @Autowired
  ListenerService listenerService;

  public void send(String queue, RabbitMqMessage rabbitMqMessage) {
      ObjectMapper mapper = new ObjectMapper();
        try {
            // Convert RabbitMqMessage object to a json string and send it as a byte array
            String jsonPayload = mapper.writeValueAsString(rabbitMqMessage);
            System.out.println("SENDING MESSAGE: " + jsonPayload);
            rabbitTemplate.convertAndSend(queue, jsonPayload.getBytes());
        } catch (JsonProcessingException | AmqpException ex) {
            Logger.getLogger(SenderService.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  public void sendLoginRequest(String email, char[] password) {
      ObjectMapper mapper = new ObjectMapper();
      RabbitMqMessage rabbitMessage = new RabbitMqMessage();
      rabbitMessage.setActionCode(ACTION_CODE_1);
      rabbitMessage.setEmail(email);
      rabbitMessage.setPassword(password);
        try {
            Queue tempQueue = listenerService.declareTemporaryQueue();
            rabbitMessage.setReturnQueueId(tempQueue.getName());
            String jsonPayload = mapper.writeValueAsString(rabbitMessage);
            System.out.println("SENDING MESSAGE: " + jsonPayload);
            rabbitTemplate.convertAndSend(SERVER_QUEUE, jsonPayload.getBytes());
        } catch (Exception ex) {
            Logger.getLogger(SenderService.class.getName()).log(Level.SEVERE, "Failed to send login request: ", ex);
        }
  }
}
