package com.algorand.auction.usecase;

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

    public void execute(String senderUserId, String receiverUserId, String notes) {
        String senderPublicKey = userRepository.getPublicKeyFor(senderUserId);
        String receiverPublicKey = userRepository.getPublicKeyFor(receiverUserId);
        try {
            transactionRepository.saveTransaction(senderPublicKey, receiverPublicKey, notes);
        } catch (Exception e) {
            logger.error("Cannot execute transaction", e);
        }
    }
}
