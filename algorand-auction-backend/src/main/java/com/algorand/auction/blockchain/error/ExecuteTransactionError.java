package com.algorand.auction.blockchain.error;

import com.algorand.auction.model.FailureError;

public class ExecuteTransactionError implements FailureError {
    private final Exception exception;

    public ExecuteTransactionError(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return "Error when executing transaction";
    }
}
