package com.algorand.auction.model;

import java.util.LinkedList;
import java.util.List;

public class AuctionBuilder {
    private int id;
    private String itemName;
    private String itemDescription;
    private List<Bid> bids = new LinkedList<>();

    public static AuctionBuilder anAuction() {
        return new AuctionBuilder();
    }

    public AuctionBuilder withId(int auctionId) {
        this.id = auctionId;
        return this;
    }
    public AuctionBuilder withItemName(String name) {
        this.itemName = name;
        return this;
    }

    public AuctionBuilder withItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }

    public AuctionBuilder withBids(Bid... bids) {
        for (Bid bid : bids) {
            this.bids.add(bid);
        }
        return this;
    }

    public Auction build() {
        Auction auction = new Auction();
        auction.setId(id);
        auction.setItemDescription(itemDescription);
        auction.setItemName(itemName);
        auction.setBids(bids);
        return auction;
    }
}