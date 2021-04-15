package com.algorand.auction.jdbc;

import com.algorand.auction.usecase.BidRepository;
import com.algorand.auction.usecase.SaveBidRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;

public class JdbcBidRepository implements BidRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcBidRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveBid(SaveBidRequest saveBidRequest) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("amount", saveBidRequest.amount)
                .addValue("userId", saveBidRequest.userId)
                .addValue("auctionId", saveBidRequest.auctionId);
        jdbcTemplate.update(
                "INSERT INTO BID (AUCTION_ID, AMOUNT, USER_ID) VALUES (:auctionId, :amount, :userId)",
                sqlParams);
    }

    @Override
    public BigDecimal getHighestBidFor(int auctionId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("auctionId", auctionId);
        return jdbcTemplate.queryForObject(
                "SELECT MAX(AMOUNT) FROM BID WHERE AUCTION_ID = :auctionId",
                sqlParams,
                BigDecimal.class
        );
    }
}
