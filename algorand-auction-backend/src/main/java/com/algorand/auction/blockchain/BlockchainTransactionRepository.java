package com.algorand.auction.blockchain;

import com.algorand.algosdk.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockchainTransactionRepository {

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

    public void saveTransaction(Account senderAccount, Account receiverAccount) throws Exception {

        if (balanceChecker.checkBalance(senderAccount.getAddress()) == 0)
            return;

        transactionSender.send(senderAccount, receiverAccount);

        balanceChecker.checkBalance(senderAccount.getAddress());
        balanceChecker.checkBalance(receiverAccount.getAddress());

    }

}
