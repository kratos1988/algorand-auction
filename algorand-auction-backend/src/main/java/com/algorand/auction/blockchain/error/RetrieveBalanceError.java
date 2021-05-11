package com.algorand.auction.blockchain.error;

import com.algorand.auction.model.FailureError;

public class RetrieveBalanceError implements FailureError {
    private final Exception exception;

    public RetrieveBalanceError(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return "Error retrieving balance";
    }
}
