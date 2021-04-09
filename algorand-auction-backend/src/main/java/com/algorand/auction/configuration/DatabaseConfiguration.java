package com.algorand.auction.configuration;

import com.algorand.auction.jdbc.JdbcAuctionRepository;
import com.algorand.auction.usecase.AuctionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public AuctionRepository auctionRepository(
            JdbcTemplate jdbcTemplate
    ) {
        return new JdbcAuctionRepository(jdbcTemplate);
    }

}
