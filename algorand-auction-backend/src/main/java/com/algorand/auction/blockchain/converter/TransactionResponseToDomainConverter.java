package com.algorand.auction.blockchain.converter;

import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;

import java.math.BigDecimal;

public class TransactionResponseToDomainConverter {

    public Transaction apply(User seller, User buyer, int amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(amount));
        transaction.setSeller(seller);
        transaction.setBuyer(buyer);
        return transaction;
    }
}
