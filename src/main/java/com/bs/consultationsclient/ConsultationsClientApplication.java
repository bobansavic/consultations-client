package com.bs.consultationsclient;

import com.bs.consultationsclient.window.ChatFrame;
import com.bs.consultationsclient.window.LoginFrame;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConsultationsClientApplication {

    public static void main(String[] args) throws IOException, TimeoutException {
//        SpringApplication.run(ConsultationsClientApplication.class, args);

        SpringApplicationBuilder builder = new SpringApplicationBuilder(ConsultationsClientApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
//        
//        System.out.println("[*****************] headless: " + System.getProperty("java.awt.headless"));
//
        EventQueue.invokeLater(() -> {
            LoginFrame frame = context.getBean(LoginFrame.class);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
