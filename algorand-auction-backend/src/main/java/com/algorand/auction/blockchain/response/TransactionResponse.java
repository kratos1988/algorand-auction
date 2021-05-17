package com.algorand.auction.blockchain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponse {
    private String note;
    private String sender;
    @JsonProperty("payment-transaction")
    private PaymentTransactionResponse paymentTransaction;

    public TransactionResponse(String note, String sender, PaymentTransactionResponse paymentTransaction) {
        this.note = note;
        this.sender = sender;
        this.paymentTransaction = paymentTransaction;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public PaymentTransactionResponse getPaymentTransaction() {
        return paymentTransaction;
    }

    public void setPaymentTransaction(PaymentTransactionResponse paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }
}
