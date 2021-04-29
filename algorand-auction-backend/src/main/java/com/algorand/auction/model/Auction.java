package com.algorand.auction.model;

import java.util.List;

public class Auction {
    private Item item;
    private List<Bid> bids;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}
