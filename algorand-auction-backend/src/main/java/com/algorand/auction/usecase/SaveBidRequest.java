package com.algorand.auction.usecase;

import java.math.BigDecimal;
import java.util.Objects;

public class SaveBidRequest {
    public final int auctionId;
    public final BigDecimal amount;
    public final String userId;

    public SaveBidRequest(int auctionId, BigDecimal amount, String userId) {
        this.auctionId = auctionId;
        this.amount = amount;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveBidRequest that = (SaveBidRequest) o;
        return auctionId == that.auctionId && Objects.equals(amount, that.amount) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctionId, amount, userId);
    }
}
