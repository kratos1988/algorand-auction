package com.algorand.auction.configuration;

import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.auction.blockchain.BalanceChecker;
import com.algorand.auction.blockchain.BlockchainTransactionHistoryRepository;
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

@Configuration
public class BlockChainConfiguration {

    @Value("${purestake.main.url}")
    private String netHost;

    @Value("${purestake.main.port}")
    private int netPort;

    @Value("${purestake.indexer.url}")
    private String indexerHost;

    @Value("${purestake.indexer.port}")
    private int indexerPort;

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
        return new AlgorandBalanceChecker(algodClient, getAlgodClientHeaders());
    }

    @Bean
    public AlgodClient algodClient(
    ) {
        return new AlgodClient(netHost, netPort, apiKey);
    }

    @Bean
    public BlockchainTransactionHistoryRepository blockchainTransactionHistoryRepository() {
        return new BlockchainTransactionHistoryRepository(
                indexerHost,
                indexerPort,
                getAlgodClientHeaders()
        );
    }


    @Bean
    public TransactionSender transactionSender(
            AlgodClient algodClient
    ) {
        AlgorandTransactionSender transactionSender = new AlgorandTransactionSender(algodClient, getAlgodClientHeaders());
        AlgorandConfirmationChecker confirmationChecker = new AlgorandConfirmationChecker(algodClient, getAlgodClientHeaders());
        return new TransactionSender(transactionSender, confirmationChecker);
    }

    private AlgodClientHeaders getAlgodClientHeaders() {
        return new AlgodClientHeaders(ALGOD_API_KEY_HEADERS, new String[]{apiKey});
    }


}
