package com.algorand.auction.usecase;

import com.algorand.auction.model.FailureError;

import java.math.BigDecimal;

public class BidAmountLessThanHighest extends FailureError {
    public BidAmountLessThanHighest(String userName, int auctionId, BigDecimal amount) {
    }
}
