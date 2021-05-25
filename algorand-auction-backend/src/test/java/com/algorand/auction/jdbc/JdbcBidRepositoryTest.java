package com.algorand.auction.jdbc;

import com.algorand.auction.model.Bid;
import com.algorand.auction.model.FailureError;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.algorand.auction.model.BidBuilder.aBid;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcBidRepositoryTest {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private JdbcBidRepository underTest;

    @BeforeAll
    public void setup() {
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
        BigDecimal amount = TEN;
        int userId = 100;
        int auctionId = 2;

        underTest.saveBid(amount, userId, auctionId);
        List<Bid> savedBids = jdbcTemplate.query(
                "SELECT * FROM BIDS WHERE AUCTION_ID = 2",
                (resultSet, index) -> {
                    Bid bid = new Bid();
                    bid.setAmount(resultSet.getBigDecimal("AMOUNT"));
                    bid.setStatus(resultSet.getString("STATUS"));
                    bid.setAuctionId(resultSet.getInt("AUCTION_ID"));
                    return bid;
                });
        assertThat(savedBids, hasSize(1));
        Bid savedBid = savedBids.get(0);
        assertThat(savedBid.getAmount(), comparesEqualTo(amount));
        assertThat(savedBid.getStatus(), is("OPEN"));
        assertThat(savedBid.getAuctionId(), is(auctionId));
    }

    @Test
    void getBids() {
        Either<FailureError,List<Bid>> result = underTest.getAllBidsFor(1);

        assertTrue(result.isRight());

        List<Bid> bids = result.get();

        assertThat(bids.size(), is(2));

        Bid firstBid = bids.get(0);
        assertThat(firstBid.getAmount(), comparesEqualTo(new BigDecimal("20.99")));
        assertThat(firstBid.getStatus(), is("ACCEPTED"));
        assertThat(firstBid.getAuctionId(), is(1));
        assertThat(firstBid.getUsername(), is("AN_USERNAME"));

        Bid secondBid = bids.get(1);
        assertThat(secondBid.getAmount(), comparesEqualTo(new BigDecimal("25.99")));
        assertThat(secondBid.getStatus(), is("ACCEPTED"));
        assertThat(secondBid.getAuctionId(), is(1));
        assertThat(secondBid.getUsername(), is("ANOTHER_USERNAME"));
    }

    @Test
    void getLastBids() {
        Either<FailureError,List<Bid>> result = underTest.getLastBidsFor(3);

        BigDecimal startingAmount = new BigDecimal("23.99");
        List<Bid> expectedList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            expectedList.add(
                    aBid()
                            .withAuctionId(3)
                            .withAmount(startingAmount.add(new BigDecimal(i)))
                            .withUsername("ANOTHER_USERNAME")
                            .withStatus("ACCEPTED")
                            .build());
        }

        assertTrue(result.isRight());

        List<Bid> bids = result.get();

        assertThat(bids.size(), is(5));

        assertThat(bids, containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    void getHighestBidFor() {
        Either<FailureError, Bid> result = underTest.getHighestBidFor(1);
        assertTrue(result.isRight());
        Bid highestBid = result.get();
        assertThat(highestBid.getAmount(), comparesEqualTo(new BigDecimal("25.99")));
        assertThat(highestBid.getAuctionId(), equalTo(1));
        assertThat(highestBid.getUsername(), equalTo("ANOTHER_USERNAME"));
    }
}