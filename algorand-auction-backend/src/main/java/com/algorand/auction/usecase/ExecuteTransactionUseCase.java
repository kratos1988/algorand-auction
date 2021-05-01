package com.algorand.auction.usecase;

import com.algorand.auction.model.Transaction;
import com.algorand.auction.usecase.repository.TransactionRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteTransactionUseCase {
    private final Logger logger = LoggerFactory.getLogger(ExecuteTransactionUseCase.class);

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public ExecuteTransactionUseCase(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public void execute(Transaction transaction) {
        try {
            transactionRepository.saveTransaction(
                    transaction.getBuyer().getPublicKey(),
                    transaction.getSeller().getPublicKey(),
                    transaction.getAmount(),
                    ""
            );
        } catch (Exception e) {
            logger.error("Cannot execute transaction", e);
        }
    }

}
