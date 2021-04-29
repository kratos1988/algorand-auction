package com.algorand.auction.model;

import java.math.BigDecimal;

public class Transaction {
    private User seller;
    private BigDecimal amount;

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
}