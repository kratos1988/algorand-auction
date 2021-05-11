package com.algorand.auction.blockchain.error;

import com.algorand.auction.model.FailureError;

public class InsufficientAmountError implements FailureError {
    private final String senderPublicKey;

    public InsufficientAmountError(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    @Override
    public String getMessage() {
        return "Error when checking balance for: " + senderPublicKey;
    }
}
