package com.algorand.auction.jdbc;

import com.algorand.auction.model.Auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuctionDto {
    public final int id;
    public final String itemName;
    public final String description;
    public final String title;
    public final BigDecimal initialValue;
    public final LocalDateTime expirationDate;
    public final String imageUrl;

    public AuctionDto(
            int id,
            String itemName,
            String description,
            String title,
            BigDecimal initialValue,
            LocalDateTime expirationDate,
            String imageUrl
    ) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.title = title;
        this.initialValue = initialValue;
        this.expirationDate = expirationDate;
        this.imageUrl = imageUrl;
    }

    public Auction toDomain() {
        Auction auction = new Auction();
        auction.setId(this.id);
        auction.setItemName(this.itemName);
        auction.setDescription(this.description);
        auction.setTitle(this.title);
        auction.setExpirationDate(this.expirationDate);
        auction.setImageUrl(this.imageUrl);
        auction.setInitialValue(this.initialValue);
        return auction;
    }

}
