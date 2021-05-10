package com.algorand.auction.blockchain.error;

import com.algorand.auction.model.FailureError;

public class InsufficientAmountError extends FailureError {
    private final String senderPublicKey;

    public InsufficientAmountError(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }
}
