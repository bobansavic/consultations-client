package com.bs.consultationsclient.service;

import com.bs.consultationsclient.model.RabbitMqMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues = {"server", "queue_1"})
public class ListenerService {
  private static final String ACTION_CODE_0 = "ACTION_CODE_0";
  private static final String ACTION_CODE_1 = "ACTION_CODE_1";
  private final String QUEUE_1 = "queue_1";

  @Autowired
  private SenderService senderService;

  @RabbitHandler
  public void recieve(byte[] in) {

    ObjectMapper mapper = new ObjectMapper();

    try {
      // JSON string to Java object
      String jsonString = new String(in, "UTF-8");
      RabbitMqMessage rabbitMqMessageIn = mapper.readValue(jsonString, RabbitMqMessage.class);

        switch(rabbitMqMessageIn.getActionCode()) {
          case ACTION_CODE_0:
            System.out.println("LOGIN FAILED!");
            break;
          case ACTION_CODE_1:
            System.out.println("LOGIN SUCCESSFUL!");
            break;
        }

        // compact print
        System.out.println(rabbitMqMessageIn);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
