package com.algorand.auction.model;

import java.util.LinkedList;
import java.util.List;

public class AuctionBuilder {
    private int id;
    private String itemName;
    private String itemDescription;
    private String title;
    private List<Bid> bids = new LinkedList<>();

    public static AuctionBuilder anAuction() {
        return new AuctionBuilder();
    }

    public AuctionBuilder withId(int auctionId) {
        this.id = auctionId;
        return this;
    }
    public AuctionBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public AuctionBuilder withItemName(String name) {
        this.itemName = name;
        return this;
    }

    public AuctionBuilder withDescription(String itemDescription) {
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
        auction.setTitle(title);
        auction.setDescription(itemDescription);
        auction.setItemName(itemName);
        auction.setBids(bids);
        return auction;
    }
}