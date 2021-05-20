package com.algorand.auction.jdbc;

import com.algorand.auction.model.FailureError;

public class DatabaseError implements FailureError {
    private final Exception e;

    public DatabaseError(Exception e) {
        this.e = e;
    }

    @Override
    public String getMessage() {
        return "Error accessing the database: " + e.getMessage();
    }
}
