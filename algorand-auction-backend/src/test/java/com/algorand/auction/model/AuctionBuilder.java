package com.algorand.auction.model;

public class AuctionBuilder {
    private String name;

    public static AuctionBuilder anAuction() {
        return new AuctionBuilder();
    }

    public AuctionBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Auction build() {
        Auction auction = new Auction();
        auction.setName(name);
        return auction;
    }
}