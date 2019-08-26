/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsclient.config;

import com.bs.consultationsclient.service.RabbitMqService;
import com.bs.consultationsclient.service.SenderService;
import com.bs.consultationsclient.window.ChatFrame;
import com.bs.consultationsclient.window.LoginFrame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Boban
 */
@Configuration
public class Config {
    
    @Bean
    public RabbitMqService messageService() {
        return new RabbitMqService();
    }

    @Bean
    public SenderService senderService() {
        return new SenderService();
    }
    
    @Bean
    public LoginFrame loginFrame() {
        return new LoginFrame();
    }
    
    @Bean
    public ChatFrame chatFrame() {
        return new ChatFrame();
    }
}
