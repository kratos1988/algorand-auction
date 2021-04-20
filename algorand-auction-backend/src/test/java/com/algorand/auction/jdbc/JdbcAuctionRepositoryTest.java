package com.algorand.auction.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

class JdbcAuctionRepositoryTest {

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("sql/schema.sql")
                .addScript("jdbc/test-data.sql")
                .build();

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    void retrieveAll() {

        JdbcAuctionRepository underTest = new JdbcAuctionRepository(jdbcTemplate);
        List<AuctionDto> retrievedAuctions = underTest.retrieveAll();
        assertThat(retrievedAuctions, is(notNullValue()));
        assertThat(retrievedAuctions, is(not(empty())));
        //TODO assertion sui campi

    }
}