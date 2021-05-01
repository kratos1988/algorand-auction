package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;
import com.algorand.auction.usecase.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class BlockchainTransactionRepository implements TransactionRepository {

    private final Logger logger = LoggerFactory.getLogger(BlockchainTransactionRepository.class);

    private final BalanceChecker balanceChecker;
    private final TransactionSender transactionSender;

    public BlockchainTransactionRepository(
            BalanceChecker balanceChecker,
            TransactionSender transactionSender
    ) {
        this.balanceChecker = balanceChecker;
        this.transactionSender = transactionSender;
    }

    public void saveTransaction(String senderPublicKey, String receiverPublicKey, BigDecimal amount, String notes) throws Exception {

        Address senderAddress = new Address(senderPublicKey);
        Address receiverAddress = new Address(receiverPublicKey);

        if (balanceChecker.checkBalance(senderAddress) == 0)
            return;

        transactionSender.send(senderAddress, receiverAddress, amount, notes);

    }

}
