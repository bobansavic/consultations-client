/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsclient.model;

import java.time.LocalDateTime;

/**
 *
 * @author Boban
 */
public class Message {
    
    private User sender;
    private User reciever;
    private String textMessage;
    private LocalDateTime timestamp;
}
