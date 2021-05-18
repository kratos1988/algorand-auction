package com.algorand.auction.rest.response;

import com.algorand.auction.model.Transaction;

import java.util.List;

public class AuthenticationResponse {
    public final String username;
    public final String token;
    public final List<Transaction> transactions;

    public AuthenticationResponse(String username, String token, List<Transaction> transactions) {
        this.username = username;
        this.token = token;
        this.transactions = transactions;
    }
}
