package com.algorand.auction.blockchain.wrapper;

import com.algorand.algosdk.account.Account;
import com.algorand.auction.blockchain.BlockchainTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;

public class AlgorandAccountCreator {

    private Logger logger = LoggerFactory.getLogger(BlockchainTransactionRepository.class);

    public Account create() {
        Account myAccount = null;
        try {
            myAccount = new Account();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Cannot generate account", e);
        }
        logger.info("Account: public key= " + myAccount.getAddress() + " private key= " + myAccount.toMnemonic());
        return myAccount;
    }
}
