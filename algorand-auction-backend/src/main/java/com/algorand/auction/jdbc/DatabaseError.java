package com.algorand.auction.jdbc;

import com.algorand.auction.model.FailureError;

public class DatabaseError extends FailureError {
    private final Exception e;

    public DatabaseError(Exception e) {
        this.e = e;
    }
}
