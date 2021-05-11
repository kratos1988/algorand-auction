package com.algorand.auction.blockchain.error;

import com.algorand.auction.model.FailureError;

public class CannotFindAddressForUser implements FailureError {
    private final String senderPublicKey;

    public CannotFindAddressForUser(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    @Override
    public String getMessage() {
        return "Cannot find address for " + senderPublicKey;
    }
}
