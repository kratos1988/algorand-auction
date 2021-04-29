package com.algorand.auction.jdbc;

import com.algorand.auction.jdbc.dto.AuctionDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuctionDtoBuilder {
    private int id;
    private String itemName;
    private String itemDescription;
    private BigDecimal initialValue;
    private String title;
    private LocalDateTime expirationDate;
    private String imageUrl;
    private int userId;

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

    public AuctionDtoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public AuctionDtoBuilder withExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public AuctionDtoBuilder withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }


    public AuctionDtoBuilder withUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public AuctionDto build() {
        return new AuctionDto(id, itemName, itemDescription, title, initialValue, expirationDate, imageUrl, userId);
    }
}