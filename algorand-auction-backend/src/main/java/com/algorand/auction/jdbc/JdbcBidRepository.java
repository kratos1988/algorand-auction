package com.algorand.auction.jdbc;

import com.algorand.auction.model.Bid;
import com.algorand.auction.usecase.BidRepository;
import com.algorand.auction.usecase.SaveBidRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcBidRepository implements BidRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcBidRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveBid(SaveBidRequest saveBidRequest) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("amount", saveBidRequest.amount)
                .addValue("userId", saveBidRequest.userId)
                .addValue("auctionId", saveBidRequest.auctionId);
        namedParameterJdbcTemplate.update(
                "INSERT INTO BIDS (AUCTION_ID, AMOUNT, USER_ID) VALUES (:auctionId, :amount, :userId)",
                sqlParams);
    }

    @Override
    public BigDecimal getHighestBidFor(int auctionId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("auctionId", auctionId);
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT MAX(AMOUNT) FROM BIDS WHERE AUCTION_ID = :auctionId",
                sqlParams,
                BigDecimal.class
        );
    }

    @Override
    public List<Bid> getAllBidsFor(int auctionId) {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM BIDS WHERE AUCTION_ID = " + auctionId,
                new BidRowMapper()
        );
    }
}
