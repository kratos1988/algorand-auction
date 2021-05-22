package com.algorand.auction.configuration;

import com.algorand.auction.blockchain.BlockchainTransactionHistoryRepository;
import com.algorand.auction.blockchain.CompositeTransactionHistoryRepository;
import com.algorand.auction.blockchain.converter.TransactionResponseToDomainConverter;
import com.algorand.auction.usecase.*;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.ItemRepository;
import com.algorand.auction.usecase.repository.TransactionRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public RetrieveAuctionsUseCase retrieveAuctionsUseCase(
            ItemRepository itemRepository,
            BidRepository bidRepository
    ) {
        return new RetrieveAuctionsUseCase(itemRepository, bidRepository);
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
            UserRepository userRepository,
            UserTokenRetriever userTokenRetriever
    ) {
        return new BidAmountForItemUseCase(bidRepository, userRepository, userTokenRetriever);
    }

    @Bean
    public RetrieveUserDataUseCase userAuthenticator(
            UserRepository userRepository,
            TransactionHistoryRepository transactionHistoryRepository,
            UserTokenRetriever userTokenRetriever
    ) {
        return new RetrieveUserDataUseCase(userRepository, transactionHistoryRepository, userTokenRetriever);
    }

    @Bean
    public UserTokenRetriever userTokenRetriever() {
        return new UserTokenRetriever();
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
