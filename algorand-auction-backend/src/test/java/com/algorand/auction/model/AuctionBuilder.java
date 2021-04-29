package com.algorand.auction.model;

import java.util.List;

public class AuctionBuilder {
    private Item item;
    private List<Bid> bids;

    public static AuctionBuilder anAuction() {
        return new AuctionBuilder();
    }

    public AuctionBuilder withItem(Item item) {
        this.item = item;
        return this;
    }

    public AuctionBuilder withBids(List<Bid> bids) {
        this.bids = bids;
        return this;
    }

    public Auction build() {
        Auction auction = new Auction();
        auction.setBids(bids);
        auction.setItem(item);
        return auction;
    }
}