package com.algorand.auction.configuration;

import com.algorand.auction.jdbc.JdbcBidRepository;
import com.algorand.auction.jdbc.JdbcItemRepository;
import com.algorand.auction.jdbc.JdbcUserRepository;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public JdbcItemRepository auctionRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcItemRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public BidRepository bidRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcBidRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public UserRepository userRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcUserRepository(namedParameterJdbcTemplate);
    }
}
