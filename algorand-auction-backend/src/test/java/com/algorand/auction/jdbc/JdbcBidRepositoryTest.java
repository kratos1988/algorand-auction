package com.algorand.auction.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

class JdbcBidRepositoryTest {

    private NamedParameterJdbcTemplate jdbcTemplate;

    JdbcBidRepository underTest;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("sql/schema.sql")
                .addScript("jdbc/test-data.sql")
                .build();

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        underTest = new JdbcBidRepository(jdbcTemplate);
    }

    @Test
    void saveBid() {
    }

    @Test
    void getHighestBidFor() {
        BigDecimal highestBid = underTest.getHighestBidFor(1);
        assertThat(highestBid, comparesEqualTo(new BigDecimal("20.99")));
    }
}