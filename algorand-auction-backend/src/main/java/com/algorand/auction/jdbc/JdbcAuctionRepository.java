package com.algorand.auction.jdbc;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.ExpiredAuction;
import com.algorand.auction.usecase.AuctionRepository;
import com.algorand.auction.usecase.BidRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JdbcAuctionRepository implements AuctionRepository {
    public static final String SELECT_ALL_AUCTIONS = "SELECT * FROM AUCTIONS";
    public static final String SELECT_AUCTION_BY_ID = "SELECT * FROM AUCTIONS WHERE ID = :id";
    public static final String SELECT_EXPIRED_AUCTIONS = "SELECT * FROM AUCTIONS WHERE EXPIRATION_DATE < NOW()";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BidRepository bidRepository;

    public JdbcAuctionRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            BidRepository bidRepository
    ) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
        this.bidRepository = bidRepository;
    }

    @Override
    public List<Auction> retrieveAll() {
        try {
            List<AuctionDto> auctionDtoList = jdbcTemplate.query(
                    SELECT_ALL_AUCTIONS,
                    new AuctionRowMapper()
            );
            return auctionDtoList.stream().map(this::convertDtoToAuction).collect(toList());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Auction retrieveBy(Integer id) {
        try {
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
            sqlParameterSource.addValue("id", id);
            AuctionDto auctionDto = jdbcTemplate.queryForObject(
                    SELECT_AUCTION_BY_ID,
                    sqlParameterSource,
                    new AuctionRowMapper()
            );

            if (auctionDto == null) {
                return null;
            }
            Auction auction = convertDtoToAuction(auctionDto);
            return auction;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ExpiredAuction> retrieveExpired() {
        List<AuctionDto> expiredAuctions = jdbcTemplate.query(
                SELECT_EXPIRED_AUCTIONS,
                new AuctionRowMapper()
        );
        return expiredAuctions.stream().map(auctionDto -> {
            ExpiredAuction expiredAuction = new ExpiredAuction();
            expiredAuction.setSeller("");
            expiredAuction.setBuyer("");
            return expiredAuction;
        }).collect(toList());
    }

    private Auction convertDtoToAuction(AuctionDto auctionDto) {
        Auction auction = auctionDto.toDomain();
        BigDecimal highestBid = bidRepository.getHighestBidFor(auctionDto.id);
        auction.setHighestBid(highestBid);
        return auction;
    }


}
