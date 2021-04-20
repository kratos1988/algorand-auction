package com.algorand.auction.jdbc;

import com.algorand.auction.usecase.AuctionRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcAuctionRepository implements AuctionRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAuctionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AuctionDto> retrieveAll() {
        return jdbcTemplate.query(
                "SELECT * " +
                        "FROM AUCTIONS",
                (rs, rowNum) ->
                        new AuctionDto(
                                rs.getInt("ID"),
                                rs.getString("ITEM_NAME"),
                                rs.getString("ITEM_DESCRIPTION"),
                                rs.getBigDecimal("INITIAL_VALUE")
                        )
        );
    }
}
