package com.algorand.auction.jdbc;

import com.algorand.auction.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
    void getPublicKey() {
        String publicKey = underTest.getPublicKeyFor("AN_USER_NAME");
        assertThat(publicKey, is("A_PUBLIC_KEY"));
    }

    @Test
    void getUserIdByUserName() {
        int publicKey = underTest.getIdByUsername("AN_USER_NAME");
        assertThat(publicKey, is(100));
    }

    @Test
    void getUserById() {
        User user = underTest.getUserById(100);
        assertThat(user.getPublicKey(), is("A_PUBLIC_KEY"));
        assertThat(user.getUserName(), is("AN_USER_NAME"));
    }


}