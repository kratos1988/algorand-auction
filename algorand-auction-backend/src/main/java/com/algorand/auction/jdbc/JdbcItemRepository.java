package com.algorand.auction.jdbc;

import com.algorand.auction.jdbc.mapper.AuctionRowMapper;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Item;
import com.algorand.auction.usecase.repository.ItemRepository;
import io.vavr.control.Either;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class JdbcItemRepository implements ItemRepository {
    public static final String SELECT_ALL_AUCTIONS = "SELECT * FROM AUCTIONS";
    public static final String SELECT_AUCTION_BY_ID = "SELECT * FROM AUCTIONS WHERE ID = :id";
    public static final String SELECT_EXPIRED_AUCTIONS = "SELECT * FROM AUCTIONS WHERE EXPIRATION_DATE < NOW() AND STATUS = 'OPEN'";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcItemRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Either<FailureError, List<Item>> retrieveAll() {
        try {
            return right(namedParameterJdbcTemplate.query(
                    SELECT_ALL_AUCTIONS,
                    new AuctionRowMapper()
            ));
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

    @Override
    public Either<FailureError,Item> retrieveBy(Integer id) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
            sqlParameterSource.addValue("id", id);
            Item item = namedParameterJdbcTemplate.queryForObject(
                    SELECT_AUCTION_BY_ID,
                    sqlParameterSource,
                    new AuctionRowMapper()
            );

            if (item == null) {
                return left(new NoRecordError(id));
            }
            return right(item);
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

    @Transactional
    @Override
    public Either<FailureError, List<Item>> retrieveExpired() {
        try {
            return right(namedParameterJdbcTemplate.query(
                    SELECT_EXPIRED_AUCTIONS,
                    new AuctionRowMapper()
            ));
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

    @Transactional
    @Override
    public Either<FailureError, Void> setStatusFinished(List<Integer> itemIds) {
        try {
            SqlParameterSource parameters = new MapSqlParameterSource("ids", itemIds);
            namedParameterJdbcTemplate.update(
                    "UPDATE AUCTIONS SET STATUS = 'PAID' WHERE ID IN (:ids)",
                    parameters
            );
            return right(null);
        } catch (Exception e) {
            return left(new DatabaseError(e));
        }
    }

}
