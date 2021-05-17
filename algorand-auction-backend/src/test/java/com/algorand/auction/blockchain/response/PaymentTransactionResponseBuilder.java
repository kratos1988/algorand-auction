package com.algorand.auction.blockchain.response;

public class PaymentTransactionResponseBuilder {
    private String receiver;
    private int amount;

    public static PaymentTransactionResponseBuilder aPaymentTransaction() {
        return new PaymentTransactionResponseBuilder();
    }

    public PaymentTransactionResponseBuilder withReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public PaymentTransactionResponseBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public PaymentTransactionResponse build() {
        PaymentTransactionResponse paymentTransactionResponse = new PaymentTransactionResponse();
        paymentTransactionResponse.setAmount(amount);
        paymentTransactionResponse.setReceiver(receiver);
        return paymentTransactionResponse;
    }
}