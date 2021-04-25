package com.algorand.auction.usecase;

public interface TransactionRepository {
    void saveTransaction(String senderPublicKey, String receiverPublicKey, String notes) throws Exception;
}
