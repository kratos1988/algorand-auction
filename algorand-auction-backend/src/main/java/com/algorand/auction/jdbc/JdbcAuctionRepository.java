package com.algorand.auction.jdbc;

import com.algorand.auction.model.Auction;
import com.algorand.auction.usecase.AuctionRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class JdbcAuctionRepository implements AuctionRepository {
    private final JdbcTemplate jdbcTemplate;
    private AuctionEntityToDomainConverter converter = new AuctionEntityToDomainConverter();

    public JdbcAuctionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Auction> retrieveAll() {
        List<AuctionDto> auctionDtos = jdbcTemplate.query(
                "SELECT ITEM_NAME " +
                        "FROM AUCTIONS",
                (rs, rowNum) ->
                        new AuctionDto(
                                rs.getString("ITEM_NAME")
                        )
        );
        return auctionDtos.stream().map(converter).collect(toList());
    }
}
