package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;
import com.algorand.auction.blockchain.error.ExecuteTransactionError;
import com.algorand.auction.blockchain.wrapper.AlgorandConfirmationChecker;
import com.algorand.auction.blockchain.wrapper.AlgorandTransactionSender;
import com.algorand.auction.model.FailureError;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

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

    public Either<FailureError, String> send(
            Address senderAddress,
            Address receiverAddress,
            BigDecimal amount,
            String notes
    ) {
        try {
            String transactionId = transactionSender.send(senderAddress, receiverAddress, amount, notes);
            confirmationChecker.waitForConfirmation(transactionId, 10);
            logger.info("Executed transaction for sender:{}, receiver:{}, amount:{}", senderAddress, receiverAddress, amount);
            return right(transactionId);
        } catch (Exception e) {
            logger.error("error when executing transaction for sender:{}, receiver:{}, amount:{}", senderAddress, receiverAddress, amount, e);
            return left(new ExecuteTransactionError(e));
        }
    }

}
