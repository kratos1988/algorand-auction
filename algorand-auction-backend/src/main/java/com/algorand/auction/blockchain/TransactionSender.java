package com.algorand.auction.blockchain;

import com.algorand.algosdk.account.Account;
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

    public void send(Account senderAccount, Account receiverAccount) throws Exception {
        String transactionId = transactionSender.send(senderAccount, receiverAccount);
        confirmationChecker.waitForConfirmation(transactionId, 5);
    }

}
