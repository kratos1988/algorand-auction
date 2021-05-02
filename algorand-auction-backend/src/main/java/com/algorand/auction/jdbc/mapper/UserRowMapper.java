package com.algorand.auction.jdbc.mapper;

import com.algorand.auction.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserName(resultSet.getString("USER_NAME"));
        user.setPublicKey(resultSet.getString("PUBLIC_KEY"));
        return user;
    }
}