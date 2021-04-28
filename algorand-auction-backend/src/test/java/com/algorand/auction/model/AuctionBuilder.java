package com.algorand.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class AuctionBuilder {
    private int id;
    private String itemName;
    private String itemDescription;
    private String title;
    private List<Bid> bids = new LinkedList<>();
    private BigDecimal initialValue;
    private LocalDateTime expirationDate;
    private String imageUrl;
    private BigDecimal highestBid;

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

    public AuctionBuilder withInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    public AuctionBuilder withExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public AuctionBuilder withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public AuctionBuilder withHighestBid(BigDecimal highestBid) {
        this.highestBid = highestBid;
        return this;
    }

    public Auction build() {
        Auction auction = new Auction();
        auction.setId(id);
        auction.setTitle(title);
        auction.setDescription(itemDescription);
        auction.setItemName(itemName);
        auction.setBids(bids);
        auction.setInitialValue(initialValue);
        auction.setExpirationDate(expirationDate);
        auction.setImageUrl(imageUrl);
        auction.setHighestBid(highestBid);
        return auction;
    }
}