package com.algorand.auction.model;

import java.math.BigDecimal;

public class TransactionBuilder {
    private User seller;
    private BigDecimal amount;
    private User buyer;

    public static TransactionBuilder aTransaction() {
        return new TransactionBuilder();
    }

    public TransactionBuilder withSeller(User seller) {
        this.seller = seller;
        return this;
    }

    public TransactionBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder withBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    public Transaction build() {
        Transaction transaction = new Transaction();
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        transaction.setAmount(amount);
        return transaction;
    }
}