package com.algorand.auction.configuration;

import com.algorand.auction.jdbc.JdbcAuctionRepository;
import com.algorand.auction.jdbc.JdbcBidRepository;
import com.algorand.auction.usecase.AuctionRepository;
import com.algorand.auction.usecase.BidRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public AuctionRepository auctionRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            BidRepository bidRepository
    ) {
        return new JdbcAuctionRepository(namedParameterJdbcTemplate, bidRepository);
    }

    @Bean
    public BidRepository bidRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcBidRepository(namedParameterJdbcTemplate);
    }
}
