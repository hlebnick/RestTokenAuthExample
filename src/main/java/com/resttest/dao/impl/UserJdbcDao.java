package com.resttest.dao.impl;

import com.resttest.dao.UserDao;
import com.resttest.dao.rowmapper.UserRowMapper;
import com.resttest.dto.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserJdbcDao implements UserDao {

    private static final Logger log = Logger.getLogger(UserJdbcDao.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User getUser(String email) {
        log.debug("Getting user by email [" + email + "]");

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        List<User> users = jdbcTemplate.query(
                "select * from users where email = :email",
                params,
                new UserRowMapper()
        );
        log.debug("Users by email [" + email + "] found: " + users.size());

        if (users.size() == 1) {
            return users.get(0);
        } else if (users.size() > 1) {
            log.debug("More than 1 user with email [" + email + "]. count: " + users.size());
            throw new IncorrectResultSizeDataAccessException(1, users.size());
        } else {
            String msg = "No users with email [" + email + "] found.";
            log.error(msg);
            return null;
        }
    }
}
