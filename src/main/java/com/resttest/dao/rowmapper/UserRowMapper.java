package com.resttest.dao.rowmapper;

import com.resttest.dto.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: HLEB
 * Date: 16.06.12
 */
public class UserRowMapper implements RowMapper<User> {

    private static final BeanPropertyRowMapper<User> USER_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return USER_MAPPER.mapRow(resultSet, rowNum);
    }
}
