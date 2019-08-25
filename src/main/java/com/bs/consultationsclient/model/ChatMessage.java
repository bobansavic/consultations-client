/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsclient.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Boban
 */
public class ChatMessage implements Serializable{
    
    private ClientUser sender;
    private ClientUser reciever;
    private String textMessage;
    private Date timestamp;
}
