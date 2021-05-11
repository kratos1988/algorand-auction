package com.algorand.auction.usecase.error;

import com.algorand.auction.model.FailureError;

import java.math.BigDecimal;

public class BidAmountLessThanHighestError implements FailureError {
    private final String userName;
    private final int auctionId;
    private final BigDecimal amount;

    public BidAmountLessThanHighestError(String userName, int auctionId, BigDecimal amount) {

        this.userName = userName;
        this.auctionId = auctionId;
        this.amount = amount;
    }

    @Override
    public String getMessage() {
        return "The bid done by " + userName + " of " + amount + " is insufficient for auctionId=" + auctionId;
    }
}
