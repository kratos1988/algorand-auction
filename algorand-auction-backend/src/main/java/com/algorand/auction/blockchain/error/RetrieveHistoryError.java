package com.algorand.auction.blockchain.error;

import com.algorand.auction.model.FailureError;

public class RetrieveHistoryError implements FailureError {
    private final Exception exception;

    public RetrieveHistoryError(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return "Error retrieving history";
    }
}
