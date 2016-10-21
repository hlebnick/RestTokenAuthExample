package com.resttest.service;

public interface TokenDao {

    void addNewToken(String token, String email);

    String getUser(String token);

    void removeToken(String token);
}
