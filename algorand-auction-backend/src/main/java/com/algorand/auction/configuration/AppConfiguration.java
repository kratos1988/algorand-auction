package com.algorand.auction.configuration;

import com.algorand.auction.blockchain.BlockchainTransactionHistoryRepository;
import com.algorand.auction.blockchain.CompositeTransactionHistoryRepository;
import com.algorand.auction.blockchain.converter.TransactionResponseToDomainConverter;
import com.algorand.auction.usecase.*;
import com.algorand.auction.usecase.repository.AuctionRepository;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.TransactionRepository;
import com.algorand.auction.usecase.repository.UserRepository;
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
    public ExecuteTransactionUseCase executeTransactionUseCase(
            TransactionRepository transactionRepository
    ) {
        return new ExecuteTransactionUseCase(transactionRepository);
    }


    @Bean
    public BidAmountForItemUseCase bidAmountForItemUseCase(
            BidRepository bidRepository,
            UserRepository userRepository
    ) {
        return new BidAmountForItemUseCase(bidRepository, userRepository);
    }

    @Bean
    public RetrieveUserDataUseCase userAuthenticator(
            UserRepository userRepository,
            TransactionHistoryRepository transactionHistoryRepository
    ) {
        return new RetrieveUserDataUseCase(userRepository, transactionHistoryRepository);
    }

    @Bean
    public TransactionHistoryRepository transactionHistoryRepository(
            BlockchainTransactionHistoryRepository blockchainTransactionHistoryRepository,
            UserRepository userRepository
    ) {
        return new CompositeTransactionHistoryRepository(
                blockchainTransactionHistoryRepository,
                userRepository,
                new TransactionResponseToDomainConverter());
    }

}
