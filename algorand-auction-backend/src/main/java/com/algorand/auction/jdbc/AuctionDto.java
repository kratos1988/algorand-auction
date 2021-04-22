package com.algorand.auction.jdbc;

import java.math.BigDecimal;

public class AuctionDto {
    public final String itemName;
    public final String description;
    public final String title;
    public final BigDecimal initialValue;
    public final int id;

    public AuctionDto(
            int id,
            String itemName,
            String description,
            String title,
            BigDecimal initialValue
    ) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.title = title;
        this.initialValue = initialValue;
    }
}
