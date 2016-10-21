package com.resttest.beans.rest;

import javax.validation.constraints.Size;

public class AuthRequest {

    private String username;
    private String password;

    @Size(min = 4)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
