package com.algorand.auction.blockchain.wrapper;

import com.algorand.algosdk.crypto.Address;
import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.model.Account;
import com.algorand.auction.blockchain.BalanceChecker;
import com.algorand.auction.blockchain.BlockchainTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlgorandBalanceChecker implements BalanceChecker {

    private Logger logger = LoggerFactory.getLogger(BlockchainTransactionRepository.class);

    private AlgodClient algodClient;
    private String[] algodApiKeyHeaders;
    private String[] algodApiKeyValues;


    public AlgorandBalanceChecker(
            AlgodClient algodClient,
            AlgodClientHeaders algodClientHeaders) {
        this.algodApiKeyHeaders = algodClientHeaders.headers;
        this.algodApiKeyValues = algodClientHeaders.values;
        this.algodClient = algodClient;
    }

    @Override
    public Long checkBalance(Address address) throws Exception {
        try {
            Account accountInfo = this.algodClient.AccountInformation(address)
                    .execute(algodApiKeyHeaders, algodApiKeyValues)
                    .body();
            logger.info("Account Balance for account public key {}: {} microAlgos", address.toString(), accountInfo.amount);
            return accountInfo.amount;
        } catch (Exception e) {
            logger.error("Error checking balance for " + address.toString(), e);
            throw e;
        }
    }
}
