package com.algorand.auction.blockchain;

import com.algorand.auction.blockchain.response.PaymentTransactionResponse;
import com.algorand.auction.blockchain.response.TransactionResponse;

public class TransactionResponseBuilder {
    private String note;
    private String sender;
    private PaymentTransactionResponse paymentTransaction;

    public static TransactionResponseBuilder aTransactionResponse() {
        return new TransactionResponseBuilder();
    }

    public TransactionResponseBuilder withNote(String note) {
        this.note = note;
        return this;
    }

    public TransactionResponseBuilder withSender(String sender) {
        this.sender = sender;
        return this;
    }

    public TransactionResponseBuilder withPaymentTransaction(PaymentTransactionResponse paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
        return this;
    }

    public TransactionResponse build() {
        return new TransactionResponse(note, sender, paymentTransaction);
    }
}