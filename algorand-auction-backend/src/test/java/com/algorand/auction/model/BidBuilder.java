package com.algorand.auction.model;

import java.math.BigDecimal;

public class BidBuilder {
    private int auctionId;
    private BigDecimal amount;
    private String status;
    private String userId;

    public static BidBuilder aBid() {
        return new BidBuilder();
    }

    public BidBuilder withAuctionId(int auctionId) {
        this.auctionId = auctionId;
        return this;
    }

    public BidBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BidBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public BidBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Bid build() {
        Bid bid = new Bid();
        bid.setAuctionId(auctionId);
        bid.setUserId(userId);
        bid.setStatus(status);
        bid.setAmount(amount);
        return bid;
    }
}