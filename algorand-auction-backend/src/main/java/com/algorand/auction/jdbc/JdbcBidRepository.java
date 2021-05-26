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
import static java.math.BigDecimal.ZERO;

public class JdbcBidRepository implements BidRepository {

    public static final String SELECT_BID_QUERY = "SELECT b.*, u.USER_NAME FROM bids b JOIN users u ON b.user_id = u.id WHERE auction_id = ";
    public static final String INSERT_BID_QUERY = "INSERT INTO BIDS (AUCTION_ID, AMOUNT, USER_ID) VALUES (:auctionId, :amount, :userId)";

    public static final String SELECT_HIGHEST_BID_AMOUNT_QUERY =
            "SELECT b1.amount " +
            "FROM bids b1 " +
            "LEFT OUTER JOIN bids b2 ON (b1.auction_id = b2.auction_id and b1.amount < b2.amount) " +
            "where b2.id is null and b1.auction_id = :auctionId";

    public static final String SELECT_HIGHEST_BID_QUERY =
            "SELECT b1.*, u.user_name " +
            "FROM bids b1 " +
            "LEFT OUTER JOIN bids b2 ON (b1.auction_id = b2.auction_id and b1.amount < b2.amount) " +
            "JOIN users u ON b1.user_id = u.id " +
            "where b2.id is null and b1.auction_id = :auctionId";
    public static final String ORDER_BY_QUERY = " ORDER BY insertion_date DESC";

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
                    INSERT_BID_QUERY,
                    sqlParams);
            return right(null);
        } catch (Exception e) {
            logger.error("Error saving the bid", e);
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError,List<Bid>> getAllBidsFor(int auctionId) {
        try {
            return right(namedParameterJdbcTemplate.query(
                    SELECT_BID_QUERY + auctionId + ORDER_BY_QUERY,
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
                    SELECT_BID_QUERY + auctionId + " " +
                            ORDER_BY_QUERY +
                            " LIMIT 5",
                    new BidRowMapper()
            ));
        } catch (Exception e) {
            logger.error("Error retrieving last bids for {}: {}", auctionId, e);
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError, Bid> getHighestBidFor(int auctionId) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("auctionId", auctionId);
            Bid bid = namedParameterJdbcTemplate.queryForObject(
                    SELECT_HIGHEST_BID_QUERY,
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
    public Either<FailureError, BigDecimal> getHighestBidAmountFor(int auctionId) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("auctionId", auctionId);
            BigDecimal amount = namedParameterJdbcTemplate.queryForObject(
                    SELECT_HIGHEST_BID_AMOUNT_QUERY,
                    sqlParams,
                    BigDecimal.class
            );
            logger.info("Retrieved highest bid for {}: {}", auctionId, amount);
            return right(amount);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No bids for auction:{}", auctionId);
            return right(ZERO);
        } catch (Exception e) {
            logger.error("Error retrieving highest bid for {}: {}", auctionId, e);
            return left(new DatabaseError(e));
        }
    }
}
