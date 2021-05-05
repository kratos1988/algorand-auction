package com.algorand.auction.usecase.error;

import com.algorand.auction.model.FailureError;

import java.math.BigDecimal;

public class BidAmountLessThanHighestError extends FailureError {
    public BidAmountLessThanHighestError(String userName, int auctionId, BigDecimal amount) {
    }
}
