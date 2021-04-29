package com.algorand.auction.configuration;

import com.algorand.auction.jdbc.JdbcAuctionRepository;
import com.algorand.auction.jdbc.JdbcBidRepository;
import com.algorand.auction.usecase.repository.BidRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public JdbcAuctionRepository auctionRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            BidRepository bidRepository
    ) {
        return new JdbcAuctionRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public BidRepository bidRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcBidRepository(namedParameterJdbcTemplate);
    }
}
