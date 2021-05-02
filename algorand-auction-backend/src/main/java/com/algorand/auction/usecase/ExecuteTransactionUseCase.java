package com.algorand.auction.usecase;

import com.algorand.auction.jdbc.DatabaseError;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Transaction;
import com.algorand.auction.usecase.repository.TransactionRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class ExecuteTransactionUseCase {
    private final Logger logger = LoggerFactory.getLogger(ExecuteTransactionUseCase.class);

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public ExecuteTransactionUseCase(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Either<FailureError, String> execute(Transaction transaction) {
        try {
            transactionRepository.saveTransaction(
                    transaction.getBuyer().getPublicKey(),
                    transaction.getSeller().getPublicKey(),
                    transaction.getAmount(),
                    ""
            );
            return right("");
        } catch (Exception e) {
            logger.error("Cannot execute transaction", e);
            return left(new DatabaseError(e));
        }
    }

}
