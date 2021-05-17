package com.algorand.auction.blockchain.response;

import java.util.List;

public class TransactionListResponse {
    private List<TransactionResponse> transactions;

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }
}
