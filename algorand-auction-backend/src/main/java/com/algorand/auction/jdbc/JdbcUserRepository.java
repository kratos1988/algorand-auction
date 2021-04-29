package com.algorand.auction.jdbc;

import com.algorand.auction.model.User;
import com.algorand.auction.usecase.repository.UserRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcUserRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public String getPublicKeyFor(String userName) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("userName", userName);
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT PUBLIC_KEY FROM USERS WHERE USER_NAME=:userName",
                sqlParams,
                String.class);
    }

    @Override
    public User getUserBy(int userId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT PUBLIC_KEY FROM USERS WHERE USER_ID=:userId",
                sqlParams,
                ((resultSet, i) -> {
                    User user = new User();
                    user.setUserName(resultSet.getString("USER_NAME"));
                    user.setPublicKey(resultSet.getString("PUBLIC_KEY"));
                    return user;
                })
        );
    }

}
