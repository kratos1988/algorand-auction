package com.algorand.auction.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
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

        assertThat(retrievedAuctions, hasSize(1));
        AuctionDto auction = retrievedAuctions.get(0);
        assertThat(auction.id, equalTo(1));
        assertThat(auction.initialValue, equalTo(new BigDecimal("10.99")));
        assertThat(auction.itemName, equalTo("AN_ITEM_NAME"));
        assertThat(auction.description, equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(auction.title, equalTo("A_TITLE"));

    }
}