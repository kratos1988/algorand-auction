package com.algorand.auction.jdbc;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Item;
import com.algorand.auction.usecase.repository.AuctionRepository;
import io.vavr.control.Either;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class JdbcAuctionRepository implements AuctionRepository {
    public static final String SELECT_ALL_AUCTIONS = "SELECT * FROM AUCTIONS";
    public static final String SELECT_AUCTION_BY_ID = "SELECT * FROM AUCTIONS WHERE ID = :id";
    public static final String SELECT_EXPIRED_AUCTIONS = "SELECT * FROM AUCTIONS WHERE EXPIRATION_DATE < NOW()";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcAuctionRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Either<FailureError, List<Item>> retrieveAll() {
        try {
            return right(jdbcTemplate.query(
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
            Item item = jdbcTemplate.queryForObject(
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

    @Override
    public List<Item> retrieveExpired() {
        return jdbcTemplate.query(
                SELECT_EXPIRED_AUCTIONS,
                new AuctionRowMapper()
        );
    }

}
