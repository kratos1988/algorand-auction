package com.algorand.auction.configuration;

import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.auction.blockchain.BalanceChecker;
import com.algorand.auction.blockchain.BlockchainTransactionRepository;
import com.algorand.auction.blockchain.TransactionSender;
import com.algorand.auction.blockchain.wrapper.AlgodClientHeaders;
import com.algorand.auction.blockchain.wrapper.AlgorandBalanceChecker;
import com.algorand.auction.blockchain.wrapper.AlgorandConfirmationChecker;
import com.algorand.auction.blockchain.wrapper.AlgorandTransactionSender;
import com.algorand.auction.usecase.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:algorand.properties")
public class BlockChainConfiguration {

    @Value("${purestake.main.url}")
    private String host;

    @Value("${purestake.main.port}")
    private int port;

    @Value("${purestake.main.api_key}")
    private String apiKey;

    private String[] ALGOD_API_KEY_HEADERS = {"X-API-Key"};

    @Bean
    public TransactionRepository transactionRepository(
            BalanceChecker balanceChecker,
            TransactionSender transactionSender
    ) {
        return new BlockchainTransactionRepository(balanceChecker, transactionSender);
    }

    @Bean
    public BalanceChecker balanceChecker(
            AlgodClient algodClient
    ) {
        return new AlgorandBalanceChecker(algodClient, ALGOD_API_KEY_HEADERS, new String[]{apiKey});
    }

    @Bean
    public AlgodClient algodClient(
    ) {
        return new AlgodClient(host, port, apiKey);
    }


    @Bean
    public TransactionSender transactionSender(
            AlgodClient algodClient
    ) {
        AlgodClientHeaders algodClientHeaders = new AlgodClientHeaders(ALGOD_API_KEY_HEADERS, new String[]{apiKey});
        AlgorandTransactionSender transactionSender = new AlgorandTransactionSender(algodClient, algodClientHeaders);
        AlgorandConfirmationChecker confirmationChecker = new AlgorandConfirmationChecker(algodClient, algodClientHeaders);
        return new TransactionSender(transactionSender, confirmationChecker);
    }


}
