package com.algorand.auction.jdbc;

import java.math.BigDecimal;

public class AuctionDtoBuilder {
    private int id;
    private String itemName;
    private String itemDescription;
    private BigDecimal initialValue;

    public static AuctionDtoBuilder anAuctionDto() {
        return new AuctionDtoBuilder();
    }

    public AuctionDtoBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public AuctionDtoBuilder withItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public AuctionDtoBuilder withItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }

    public AuctionDtoBuilder withInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    public AuctionDto build() {
        return new AuctionDto(id, itemName, itemDescription, initialValue);
    }
}