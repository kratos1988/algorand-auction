package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;
import com.algorand.auction.blockchain.wrapper.AlgorandConfirmationChecker;
import com.algorand.auction.blockchain.wrapper.AlgorandTransactionSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionSender {

    private final Logger logger = LoggerFactory.getLogger(TransactionSender.class);

    private final AlgorandTransactionSender transactionSender;
    private final AlgorandConfirmationChecker confirmationChecker;

    public TransactionSender(
            AlgorandTransactionSender transactionSender, AlgorandConfirmationChecker confirmationChecker
    ) {
        this.transactionSender = transactionSender;
        this.confirmationChecker = confirmationChecker;
    }

    public void send(Address senderPublicKey, Address receiverPublicKey, String notes) throws Exception {
        String transactionId = transactionSender.send(senderPublicKey, receiverPublicKey, notes);
        confirmationChecker.waitForConfirmation(transactionId, 5);
    }

}
