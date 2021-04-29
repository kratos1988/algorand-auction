package com.algorand.auction.usecase;

import java.math.BigDecimal;
import java.util.Objects;

public class SaveBidRequest {
    public final int auctionId;
    public final BigDecimal amount;
    public final int userId;

    public SaveBidRequest(int auctionId, BigDecimal amount, int userId) {
        this.auctionId = auctionId;
        this.amount = amount;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveBidRequest that = (SaveBidRequest) o;
        return auctionId == that.auctionId && userId == that.userId && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctionId, amount, userId);
    }
}
