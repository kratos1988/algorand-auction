package com.algorand.auction.blockchain.error;

import com.algorand.auction.model.FailureError;

public class CannotFindAddressForUser extends FailureError {
    private final Exception e;

    public CannotFindAddressForUser(Exception e) {
        this.e = e;
    }
}
