package com.algorand.auction.configuration;

import com.algorand.auction.usecase.AuctionRepository;
import com.algorand.auction.usecase.BidAmountForItemUseCase;
import com.algorand.auction.usecase.BidRepository;
import com.algorand.auction.usecase.RetrieveAuctionsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public RetrieveAuctionsUseCase retrieveAuctionsUseCase(
            AuctionRepository auctionRepository,
            BidRepository bidRepository
    ) {
        return new RetrieveAuctionsUseCase(auctionRepository, bidRepository);
    }

    @Bean
    public BidAmountForItemUseCase bidAmountForItemUseCase(BidRepository bidRepository) {
        return new BidAmountForItemUseCase(bidRepository);
    }
}
