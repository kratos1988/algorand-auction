package com.algorand.auction.model;

import java.math.BigDecimal;
import java.util.List;

public class Auction {
    private int id;
    private String itemName;
    private String itemDescription;
    private List<Bid> bids;
    private BigDecimal initialValue;
    private BigDecimal highestBid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public BigDecimal getInitialValue() {
        return initialValue;
    }

    public void setHighestBid(BigDecimal highestBid) {
        this.highestBid = highestBid;
    }

    public BigDecimal getHighestBid() {
        return highestBid;
    }
}
