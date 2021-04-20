package com.algorand.auction.jdbc;

import java.math.BigDecimal;

public class AuctionDto {
    public final String itemName;
    public final String itemDescription;
    public final BigDecimal initialValue;
    public final int id;

    public AuctionDto(
            int id,
            String itemName,
            String itemDescription,
            BigDecimal initialValue
    ) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.initialValue = initialValue;
    }
}
