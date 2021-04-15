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

import static com.algorand.auction.model.BidBuilder.aBid;
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
    public void setup(){
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
        underTest.saveBid(new SaveBidRequest(2, TEN, "ANOTHER_USER_ID"));
        Bid savedBid = jdbcTemplate.queryForObject("SELECT * FROM BID WHERE USER_ID = 'ANOTHER_USER_ID'", emptyMap(), (resultSet, i) -> {
            return aBid()
                    .withAmount(resultSet.getBigDecimal("AMOUNT"))
                    .withStatus(resultSet.getString("STATUS"))
                    .withAuctionId(resultSet.getInt("AUCTION_ID"))
                    .withUserId(resultSet.getString("USER_ID"))
                    .build();
        });
        assertThat(savedBid, is(notNullValue()));
        assertThat(savedBid.getAmount(), comparesEqualTo(TEN));
        assertThat(savedBid.getStatus(), is("INSERTED"));
        assertThat(savedBid.getAuctionId(), is(2));
        assertThat(savedBid.getUserId(), is("ANOTHER_USER_ID"));
    }

    @Test
    void getHighestBidFor() {
        BigDecimal highestBid = underTest.getHighestBidFor(1);
        assertThat(highestBid, comparesEqualTo(new BigDecimal("20.99")));
    }
}