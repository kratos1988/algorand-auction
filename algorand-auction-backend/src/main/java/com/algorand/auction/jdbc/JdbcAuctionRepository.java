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
                new AuctionRowMapper()
        );
    }

    @Override
    public AuctionDto retrieveBy(Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * " +
                        "FROM AUCTIONS WHERE ID = " + id,
                new AuctionRowMapper()
        );
    }
}
