package com.algorand.auction.jdbc;

import com.algorand.auction.model.FailureError;

public class NoRecordError extends FailureError {
    public NoRecordError(Integer entityId) {
    }
}
