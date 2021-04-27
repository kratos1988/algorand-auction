package com.algorand.auction.jdbc;

import com.algorand.auction.model.User;
import com.algorand.auction.usecase.UserRepository;
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

    class UserDto {
        public final String userName;
        public final String publicKey;

        UserDto(String userName, String publicKey) {
            this.userName = userName;
            this.publicKey = publicKey;
        }

        public User toDomain() {
            User user = new User();
            user.setUserName(userName);
            user.setPublicKey(publicKey);
            return user;
        }
    }
}
