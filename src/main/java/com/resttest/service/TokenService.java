package com.resttest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenDao tokenDao;

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String registerUser(String email) {
        String token = generateToken();
        tokenDao.addNewToken(token, email);
        return token;
    }

    public void removeUser(String token) {
        tokenDao.removeToken(token);
    }

    public String getUser(String token) {
        return tokenDao.getUser(token);
    }
}
