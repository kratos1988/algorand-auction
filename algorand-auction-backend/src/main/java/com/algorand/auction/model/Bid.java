package com.algorand.auction.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Bid {
    private int auctionId;
    private BigDecimal amount;
    private String status;
    private String username;

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return auctionId == bid.auctionId && Objects.equals(amount, bid.amount) && Objects.equals(status, bid.status) && Objects.equals(username, bid.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctionId, amount, status, username);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "auctionId=" + auctionId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
