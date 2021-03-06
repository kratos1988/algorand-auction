package com.algorand.auction.jdbc;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.User;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcUserRepositoryTest {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private JdbcUserRepository underTest;
    private static DataSource dataSource;

    @BeforeAll
    public void setup() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("sql/schema.sql")
                .addScript("jdbc/test-data.sql")
                .build();

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        underTest = new JdbcUserRepository(jdbcTemplate);
    }

    @Test
    void getUserIdByUserName() {
        Either<FailureError, Integer> result = underTest.getIdByUsername("AN_USERNAME");
        assertTrue(result.isRight());
        int publicKey = result.get();
        assertThat(publicKey, is(100));
    }

    @Test
    void getUserByPublicKey() {
        Either<FailureError, User> result = underTest.getUserByPublicKey("A_PUBLIC_KEY");
        assertTrue(result.isRight());
        User user = result.get();
        assertThat(user.getPublicKey(), is("A_PUBLIC_KEY"));
        assertThat(user.getUserName(), is("AN_USERNAME"));
    }

    @Test
    void getUserByUsername() {
        Either<FailureError, User> result = underTest.getUserByUsername("AN_USERNAME");
        assertTrue(result.isRight());
        User user = result.get();
        assertThat(user.getPublicKey(), is("A_PUBLIC_KEY"));
        assertThat(user.getUserName(), is("AN_USERNAME"));
    }

    @Test
    void authenticateUser() {
        Either<FailureError, User> result = underTest.authenticate("AN_USERNAME", "A_PASSWORD");
        assertTrue(result.isRight());
        User user = result.get();
        assertThat(user.getPublicKey(), is("A_PUBLIC_KEY"));
        assertThat(user.getUserName(), is("AN_USERNAME"));
    }

}