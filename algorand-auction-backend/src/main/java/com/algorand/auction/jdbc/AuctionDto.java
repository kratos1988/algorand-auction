package com.algorand.auction.jdbc;

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
}
