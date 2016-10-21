package com.resttest.dao;

import com.resttest.dto.User;

public interface UserDao {

    User getUser(String email);
}
