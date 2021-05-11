package com.algorand.auction.jdbc;

import com.algorand.auction.model.FailureError;

public class NoRecordError implements FailureError {
    private final Integer entityId;

    public NoRecordError(Integer entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getMessage() {
        return "No entity for id:" + entityId;
    }
}
