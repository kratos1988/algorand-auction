package com.algorand.auction.jdbc;

import com.algorand.auction.jdbc.mapper.UserRowMapper;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.User;
import com.algorand.auction.usecase.repository.UserRepository;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class JdbcUserRepository implements UserRepository {
    private final Logger logger = LoggerFactory.getLogger(JdbcUserRepository.class);

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
    public Either<FailureError, Integer> getIdByUsername(String userName) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("userName", userName);
            Integer userId = namedParameterJdbcTemplate.queryForObject(
                    "SELECT ID FROM USERS WHERE USER_NAME=:userName",
                    sqlParams,
                    Integer.class);
            if (userId == null)
                return left(new NoRecordError(0)); // TODO fix field
            return right(userId);
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError, User> authenticate(String username, String password) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("username", username)
                    .addValue("password", password);
            User user = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM USERS WHERE USER_NAME=:username AND PASSWORD=:password",
                    sqlParams,
                    new UserRowMapper());
            if (user == null)
                return left(new NoRecordError(0));
            return right(user);
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError, User> getUserByPublicKey(String publicKey) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("publicKey", publicKey);
            User user = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM USERS WHERE PUBLIC_KEY=:publicKey",
                    sqlParams,
                    new UserRowMapper());
            if (user == null)
                return left(new NoRecordError(0)); // TODO fix field
            return right(user);
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError, User> getUserByUsername(String username) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("username", username);
            User user = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM USERS WHERE USER_NAME=:username",
                    sqlParams,
                    new UserRowMapper()
            );
            if (user == null)
                return left(new NoRecordError(0));
            logger.info("Found user by id: {}", username, user);
            return right(user);
        } catch (Exception e) {
            logger.error("Error retrieving user by id: {}", username, e);
            return left(new DatabaseError(e));
        }
    }

}
