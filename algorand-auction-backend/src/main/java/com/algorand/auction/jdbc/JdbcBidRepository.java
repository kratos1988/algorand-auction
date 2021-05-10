package com.algorand.auction.jdbc;

import com.algorand.auction.jdbc.mapper.BidRowMapper;
import com.algorand.auction.model.Bid;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.usecase.repository.BidRepository;
import io.vavr.control.Either;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class JdbcBidRepository implements BidRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcBidRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Either<FailureError, Void> saveBid(BigDecimal amount, int userId, int auctionId) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("amount", amount)
                    .addValue("userId", userId)
                    .addValue("auctionId", auctionId);
            namedParameterJdbcTemplate.update(
                    "INSERT INTO BIDS (AUCTION_ID, AMOUNT, USER_ID) VALUES (:auctionId, :amount, :userId)",
                    sqlParams);
            return right(null);
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError, Bid> getHighestBidFor(int auctionId) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("auctionId", auctionId);
            Bid bid = namedParameterJdbcTemplate.queryForObject(
                    "SELECT b1.* FROM BIDS b1 " +
                            "LEFT OUTER JOIN BIDS b2 ON (b1.auction_id = b2.auction_id and b1.amount < b2.amount) " +
                            "where b2.id is null and b1.auction_id = :auctionId",
                    sqlParams,
                    new BidRowMapper()
            );
            if (bid == null) {
                return left(new NoRecordError(auctionId));
            }
            return right(bid);
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError,List<Bid>> getAllBidsFor(int auctionId) {
        try {
            return right(namedParameterJdbcTemplate.query(
                    "SELECT * FROM BIDS WHERE AUCTION_ID = " + auctionId,
                    new BidRowMapper()
            ));
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError, List<Bid>> getLastBidsFor(int auctionId) {
        try {
            return right(namedParameterJdbcTemplate.query(
                    "SELECT * FROM BIDS WHERE AUCTION_ID = " + auctionId + " LIMIT 5",
                    new BidRowMapper()
            ));
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }
}
