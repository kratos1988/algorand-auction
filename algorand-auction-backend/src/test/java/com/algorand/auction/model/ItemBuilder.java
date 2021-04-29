package com.algorand.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ItemBuilder {
    private int id;
    private String itemName;
    private String itemDescription;
    private String title;
    private BigDecimal initialValue;
    private LocalDateTime expirationDate;
    private String imageUrl;
    private BigDecimal highestBid;

    public static ItemBuilder anItem() {
        return new ItemBuilder();
    }

    public ItemBuilder withId(int auctionId) {
        this.id = auctionId;
        return this;
    }
    public ItemBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder withItemName(String name) {
        this.itemName = name;
        return this;
    }

    public ItemBuilder withDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }


    public ItemBuilder withInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    public ItemBuilder withExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public ItemBuilder withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ItemBuilder withHighestBid(BigDecimal highestBid) {
        this.highestBid = highestBid;
        return this;
    }

    public Item build() {
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setDescription(itemDescription);
        item.setItemName(itemName);
        item.setInitialValue(initialValue);
        item.setExpirationDate(expirationDate);
        item.setImageUrl(imageUrl);
        item.setHighestBid(highestBid);
        return item;
    }
}