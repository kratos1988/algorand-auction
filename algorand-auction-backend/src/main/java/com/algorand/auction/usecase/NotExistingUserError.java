package com.algorand.auction.usecase;

import com.algorand.auction.model.FailureError;

public class NotExistingUserError implements FailureError {
    private final String token;

    public NotExistingUserError(String token) {
        this.token = token;
    }

    @Override
    public String getMessage() {
        return "User not existing for token:" + token;
    }
}
