package com.algorand.auction.usecase.repository;

import java.math.BigDecimal;

public interface TransactionRepository {
    void saveTransaction(String senderPublicKey, String receiverPublicKey, BigDecimal amount, String notes) throws Exception;
}
