package com.bs.consultationsclient.service;

import com.bs.consultationsclient.model.RabbitMqMessage;
import com.bs.consultationsclient.model.UserDto;
import com.bs.consultationsclient.util.Session;
import com.bs.consultationsclient.window.LoginFrame;
import com.bs.consultationsclient.window.RegisterFrame;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import javax.swing.JOptionPane;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(id = "myListener", queues = {"queue_1", "RETURN_QUEUE_REGISTER_1"})
public class ListenerService {

    @Autowired
    private RabbitListenerEndpointRegistry registry;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RegisterFrame registerFrame;

    @Autowired
    private LoginFrame loginFrame;

    @Autowired
    private Session session;

    private static final String ACTION_CODE_0 = "ACTION_CODE_0";
    private static final String ACTION_CODE_0_0 = "ACTION_CODE_0_0";
    private static final String ACTION_CODE_0_1 = "ACTION_CODE_0_1";
    private static final String ACTION_CODE_1 = "ACTION_CODE_1";
    private static final String ACTION_CODE_1_1 = "ACTION_CODE_1_1";
    private final String QUEUE_1 = "queue_1";
    private RabbitMqMessage rabbitMessageIn;
    AbstractMessageListenerContainer container;

    @Autowired
    private SenderService senderService;

    @RabbitHandler
    public void recieve(byte[] in) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonString = new String(in, "UTF-8");
            System.out.println("INCOMMING MESSAGE:\n" + jsonString);
            rabbitMessageIn = mapper.readValue(jsonString, RabbitMqMessage.class);

            switch (rabbitMessageIn.getActionCode()) {
                case ACTION_CODE_0_0:
                    System.out.println("REGISTRATION FAILED: " + rabbitMessageIn.getErrorMessage());
                    JOptionPane.showMessageDialog(null, "Error: " + rabbitMessageIn.getErrorMessage(), "Registration error", JOptionPane.ERROR_MESSAGE);
                    container = (AbstractMessageListenerContainer) registry.getListenerContainer("myListener");
                    try {
                        Queue sessionQueue = rabbitAdmin.declareQueue();
                        System.out.println(sessionQueue.getName() + " declared successfully!");
                        container.addQueueNames(sessionQueue.getName());
                        System.out.println(sessionQueue.getName() + " added successfully!");
                        container.removeQueueNames(sessionQueue.getName());
                        System.out.println(sessionQueue.getName() + " removed successfully!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("Queues in container:");
                    for (String queueName : container.getQueueNames()) {
                        System.out.println(queueName);
                    }
                    break;
                case ACTION_CODE_0_1:
                    System.out.println("REGISTRATION SUCCESS!");
                    JOptionPane.showMessageDialog(null, "Successfully registered with email " + rabbitMessageIn.getEmail() + "!", "Registration successful", JOptionPane.PLAIN_MESSAGE);
                    registerFrame.reset();
                    registerFrame.setVisible(false);
                    loginFrame.reset();
                    loginFrame.setVisible(true);
                    break;
                case ACTION_CODE_1_1:
                    System.out.println("LOGIN SUCCESSFUL!");
                    JOptionPane.showMessageDialog(null, "Login successful!", "Registration successful", JOptionPane.PLAIN_MESSAGE);
                    UserDto activeUser = new UserDto(rabbitMessageIn.getUserId(), rabbitMessageIn.getFirstName(),
                        rabbitMessageIn.getLastName(), rabbitMessageIn.getEmail(), rabbitMessageIn.getUniqueIdentifier());
                    session.setActiveUser(activeUser);
                    session.setClientMessages(rabbitMessageIn.getClientMessages());
                    Queue activeUserQueue = new Queue(rabbitMessageIn.getUniqueIdentifier(), true);
                    rabbitAdmin.declareQueue(activeUserQueue);
                    container = (AbstractMessageListenerContainer) registry.getListenerContainer("myListener");
                    container.addQueueNames(activeUserQueue.getName());
                    break;
            }

            // compact print
            System.out.println(rabbitMessageIn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Queue declareTemporaryQueue() throws Exception {
        AbstractMessageListenerContainer container = (AbstractMessageListenerContainer) registry.getListenerContainer("myListener");
        Queue tempQueue = rabbitAdmin.declareQueue();
        container.addQueueNames(tempQueue.getName());
        System.out.println("Temporary queue " + tempQueue.getName() + " declared successfully!");
        return tempQueue;
    }
}
