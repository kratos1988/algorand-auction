package com.algorand.auction.jdbc;

import com.algorand.auction.model.Bid;
import com.algorand.auction.usecase.SaveBidRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.TEN;
import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcBidRepositoryTest {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private JdbcBidRepository underTest;
    private static DataSource dataSource;

    @BeforeAll
    public void setup() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("sql/schema.sql")
                .addScript("jdbc/test-data.sql")
                .build();

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        underTest = new JdbcBidRepository(jdbcTemplate);
    }

    @Test
    void saveBid() {
        underTest.saveBid(new SaveBidRequest(2, TEN, 100));
        Bid savedBid = jdbcTemplate.queryForObject(
                "SELECT * FROM BIDS WHERE AUCTION_ID = 2",
                emptyMap(),
                new BidRowMapper());
        assertThat(savedBid.getAmount(), comparesEqualTo(TEN));
        assertThat(savedBid.getStatus(), is("INSERTED"));
        assertThat(savedBid.getAuctionId(), is(2));
        assertThat(savedBid.getUserId(), is(100));
    }

    @Test
    void getBids() {
        List<Bid> bids = underTest.getAllBidsFor(1);
        assertThat(bids.size(), is(2));

        Bid firstBid = bids.get(0);
        assertThat(firstBid.getAmount(), comparesEqualTo(new BigDecimal("20.99")));
        assertThat(firstBid.getStatus(), is("ACCEPTED"));
        assertThat(firstBid.getAuctionId(), is(1));
        assertThat(firstBid.getUserId(), is(100));

        Bid secondBid = bids.get(1);
        assertThat(secondBid.getAmount(), comparesEqualTo(new BigDecimal("25.99")));
        assertThat(secondBid.getStatus(), is("ACCEPTED"));
        assertThat(secondBid.getAuctionId(), is(1));
        assertThat(secondBid.getUserId(), is(101));
    }

    @Test
    void getHighestBidFor() {
        Bid highestBid = underTest.getHighestBidFor(1);
        assertThat(highestBid.getAmount(), comparesEqualTo(new BigDecimal("25.99")));
        assertThat(highestBid.getAuctionId(), equalTo(1));
        assertThat(highestBid.getUserId(), equalTo(101));
    }
}