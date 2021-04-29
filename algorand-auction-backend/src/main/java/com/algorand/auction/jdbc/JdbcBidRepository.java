package com.algorand.auction.jdbc;

import com.algorand.auction.model.Bid;
import com.algorand.auction.usecase.SaveBidRequest;
import com.algorand.auction.usecase.repository.BidRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
    public Bid getHighestBidFor(int auctionId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("auctionId", auctionId);
        return namedParameterJdbcTemplate.queryForObject(
                    "SELECT b1.* FROM BIDS b1 " +
                            "LEFT OUTER JOIN BIDS b2 ON (b1.auction_id = b2.auction_id and b1.amount < b2.amount) " +
                            "where b2.id is null and b1.auction_id = :auctionId",
                sqlParams,
                new BidRowMapper()
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
