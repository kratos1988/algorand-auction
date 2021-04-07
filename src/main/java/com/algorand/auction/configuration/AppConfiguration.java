package com.algorand.auction.configuration;

import com.algorand.auction.usecase.RetrieveAuctionsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public RetrieveAuctionsUseCase retrieveAuctionsUseCase() {
        return new RetrieveAuctionsUseCase(null);
    }
}
