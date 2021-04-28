package com.algorand.auction.jdbc;

import com.algorand.auction.model.Auction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

class JdbcAuctionRepositoryTest {

    public static final LocalDateTime EXPIRATION_DATE = LocalDateTime.of(2021, 4, 12, 7, 20);

    private NamedParameterJdbcTemplate jdbcTemplate;
    private JdbcAuctionRepository underTest;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("sql/schema.sql")
                .addScript("jdbc/test-data.sql")
                .build();

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        underTest = new JdbcAuctionRepository(jdbcTemplate, new JdbcBidRepository(jdbcTemplate));
    }

    @Test
    void retrieveAll() {

        List<Auction> retrievedAuctions = underTest.retrieveAll();

        assertThat(retrievedAuctions, hasSize(1));
        Auction auction = retrievedAuctions.get(0);
        assertThat(auction.getId(), equalTo(1));
        assertThat(auction.getInitialValue(), equalTo(new BigDecimal("10.99")));
        assertThat(auction.getItemName(), equalTo("AN_ITEM_NAME"));
        assertThat(auction.getDescription(), equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(auction.getTitle(), equalTo("A_TITLE"));
        assertThat(auction.getExpirationDate(), is(equalTo(EXPIRATION_DATE)));
        assertThat(auction.getImageUrl(), equalTo("AN_IMAGE_URL"));
        assertThat(auction.getHighestBid(), equalTo(new BigDecimal("20.99")));

    }

    @Test
    void retrieveById() {

        Auction auction = underTest.retrieveBy(1);

        assertThat(auction.getId(), equalTo(1));
        assertThat(auction.getInitialValue(), equalTo(new BigDecimal("10.99")));
        assertThat(auction.getItemName(), equalTo("AN_ITEM_NAME"));
        assertThat(auction.getDescription(), equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(auction.getTitle(), equalTo("A_TITLE"));
        assertThat(auction.getExpirationDate(), is(equalTo(EXPIRATION_DATE)));
        assertThat(auction.getImageUrl(), equalTo("AN_IMAGE_URL"));
        assertThat(auction.getHighestBid(), equalTo(new BigDecimal("20.99")));
        assertThat(auction.getBids(), is(not(empty())));

    }
}