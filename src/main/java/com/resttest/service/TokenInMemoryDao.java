package com.resttest.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class TokenInMemoryDao implements TokenDao {

    private static final Logger log = Logger.getLogger(TokenInMemoryDao.class);

    private HashMap<String, String> tokensStorage = new HashMap<>();

    @Override
    public void addNewToken(String token, String email) {
        tokensStorage.put(token, email);
        log.info("New token '" + token + "' was put into storage for user '" + email + "'");
    }

    @Override
    public String getUser(String token) {
        return tokensStorage.get(token);
    }

    @Override
    public void removeToken(String token) {
        tokensStorage.remove(token);
        log.info("Removing token '" + token);
    }
}
