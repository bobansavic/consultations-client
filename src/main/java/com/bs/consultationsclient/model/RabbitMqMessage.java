/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsclient.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Boban
 */
public class RabbitMqMessage implements Serializable{
    private String actionCode;
    private Long userId;
    private String FirstName;
    private String LastName;
    private String email;
    private char[] password;
    private MessageDto messageDto;
    private String returnQueueId;
    private String errorMessage;
    private String uniqueIdentifier;
    private Map<Long, List<MessageDto>> clientMessages;

    public RabbitMqMessage() {
    }
    
    public RabbitMqMessage(String actionCode, String email, char[] password, String returnQueueId) {
        this.actionCode = actionCode;
        this.email = email;
        this.password = password;
        this.returnQueueId = returnQueueId;
    }

    public RabbitMqMessage(String actionCode, String email, char[] password, MessageDto messageDto, String returnQueueId) {
        this.actionCode = actionCode;
        this.email = email;
        this.password = password;
        this.messageDto = messageDto;
        this.returnQueueId = returnQueueId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public MessageDto getMessageDto() {
        return messageDto;
    }

    public void setMessageDto(MessageDto messageDto) {
        this.messageDto = messageDto;
    }
    
    public String getReturnQueueId() {
        return returnQueueId;
    }

    public void setReturnQueueId(String queueId) {
        this.returnQueueId = queueId;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public Map<Long, List<MessageDto>> getClientMessages() {
        return clientMessages;
    }

    public void setClientMessages(
        Map<Long, List<MessageDto>> clientMessages) {
        this.clientMessages = clientMessages;
    }
}
