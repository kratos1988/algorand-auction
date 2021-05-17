package com.algorand.auction.blockchain.response;

public class PaymentTransactionResponse {
    private String receiver;
    private int amount;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
