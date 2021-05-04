package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;
import com.algorand.auction.blockchain.error.CannotFindAddressForUser;
import com.algorand.auction.blockchain.error.InsufficientAmountError;
import com.algorand.auction.blockchain.error.RetrieveBalanceError;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.usecase.repository.TransactionRepository;
import io.vavr.Tuple;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

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

    public Either<FailureError, String> saveTransaction(
            String senderPublicKey,
            String receiverPublicKey,
            BigDecimal amount,
            String notes
    ) {
        return searchAddressFor(senderPublicKey)
                .flatMap(senderAddress -> searchAddressFor(receiverPublicKey).map(receiverAddress -> Tuple.of(senderAddress, receiverAddress)))
                .flatMap(input -> checkBalanceForSender(input._1).map(voidInput -> input))
                .flatMap(input -> transactionSender.send(input._1, input._2, amount, notes));


    }

    private Either<FailureError,Address> searchAddressFor(String senderPublicKey) {
        try {
            return right(new Address(senderPublicKey));
        } catch (Exception e) {
            return left(new CannotFindAddressForUser(e));
        }
    }

    private Either<FailureError, Void> checkBalanceForSender(Address senderAddress) {
        try {
            if (balanceChecker.checkBalance(senderAddress) == 0)
                return left(new InsufficientAmountError(senderAddress.encodeAsString()));
            else
                return right(null);
        } catch (Exception e) {
            return left(new RetrieveBalanceError(e));
        }
    }

}
