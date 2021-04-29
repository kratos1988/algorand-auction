package com.algorand.auction.usecase.repository;

public interface TransactionRepository {
    void saveTransaction(String senderPublicKey, String receiverPublicKey, String notes) throws Exception;
}
