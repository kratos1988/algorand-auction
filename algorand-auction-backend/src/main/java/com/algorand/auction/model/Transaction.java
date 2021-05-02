package com.algorand.auction.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {
    private User seller;
    private BigDecimal amount;
    private User buyer;

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getSeller() {
        return seller;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getBuyer() {
        return buyer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(seller, that.seller) && Objects.equals(amount, that.amount) && Objects.equals(buyer, that.buyer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seller, amount, buyer);
    }
}
