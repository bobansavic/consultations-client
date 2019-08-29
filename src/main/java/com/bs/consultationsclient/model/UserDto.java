/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsclient.model;

import java.io.Serializable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boban
 */
@Service
public class UserDto implements Serializable{

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String uniqueIdentifier;

    public UserDto() {
    }

    public UserDto(Long userId, String firstName, String lastName, String email,
        String uniqueIdentifier) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
