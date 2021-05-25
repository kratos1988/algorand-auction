package com.algorand.auction.jdbc;

import com.algorand.auction.jdbc.mapper.BidRowMapper;
import com.algorand.auction.model.Bid;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.usecase.repository.BidRepository;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class JdbcBidRepository implements BidRepository {

    private final Logger logger = LoggerFactory.getLogger(JdbcBidRepository.class);

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
            logger.error("Error saving the bid", e);
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError, Bid> getHighestBidFor(int auctionId) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("auctionId", auctionId);
            Bid bid = namedParameterJdbcTemplate.queryForObject(
                    "SELECT b1.*, u.user_name " +
                            "FROM bids b1 " +
                            "LEFT OUTER JOIN bids b2 ON (b1.auction_id = b2.auction_id and b1.amount < b2.amount) " +
                            "JOIN users u ON b1.user_id = u.id " +
                            "where b2.id is null and b1.auction_id = :auctionId",
                    sqlParams,
                    new BidRowMapper()
            );
            logger.info("Retrieved highest bid for {}: {}", auctionId, bid);
            return right(bid);
        } catch (EmptyResultDataAccessException e) {
            return left(new NoRecordError(auctionId));
        } catch (Exception e) {
            logger.error("Error retrieving highest bid for {}: {}", auctionId, e);
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError,List<Bid>> getAllBidsFor(int auctionId) {
        try {
            return right(namedParameterJdbcTemplate.query(
                    "SELECT b.*, u.USER_NAME " +
                            "FROM BIDS b " +
                            "JOIN USERS u ON b.USER_ID = u.ID " +
                            "WHERE AUCTION_ID = " + auctionId + " " +
                            "ORDER BY INSERTION_DATE DESC",
                    new BidRowMapper()
            ));
        } catch (Exception e) {
            logger.error("Error retrieving all bids for {}: {}", auctionId, e);
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError, List<Bid>> getLastBidsFor(int auctionId) {
        try {
            return right(namedParameterJdbcTemplate.query(
                    "SELECT b.*, u.USER_NAME " +
                            "FROM BIDS b " +
                            "JOIN USERS u ON b.USER_ID = u.ID " +
                            "WHERE AUCTION_ID = " + auctionId + " " +
                            "ORDER BY INSERTION_DATE DESC " +
                            "LIMIT 5",
                    new BidRowMapper()
            ));
        } catch (Exception e) {
            logger.error("Error retrieving last bids for {}: {}", auctionId, e);
            return left(new DatabaseError(e));
        }
    }
}
