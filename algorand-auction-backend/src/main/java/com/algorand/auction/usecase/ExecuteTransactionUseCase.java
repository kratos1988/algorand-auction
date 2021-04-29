package com.algorand.auction.usecase;

import com.algorand.auction.model.User;
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

    public void execute(String senderUserId, User receiverUserId, String notes) {
        String senderPublicKey = userRepository.getPublicKeyFor(senderUserId);
        try {
            transactionRepository.saveTransaction(senderPublicKey, receiverUserId.getPublicKey(), notes);
        } catch (Exception e) {
            logger.error("Cannot execute transaction", e);
        }
    }
}
