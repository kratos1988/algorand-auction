package com.algorand.auction.jdbc.dto;

import com.algorand.auction.model.Item;

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
    public final int userId;

    public AuctionDto(
            int id,
            String itemName,
            String description,
            String title,
            BigDecimal initialValue,
            LocalDateTime expirationDate,
            String imageUrl,
            int userId) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.title = title;
        this.initialValue = initialValue;
        this.expirationDate = expirationDate;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public Item toDomain() {
        Item item = new Item();
        item.setId(this.id);
        item.setItemName(this.itemName);
        item.setDescription(this.description);
        item.setTitle(this.title);
        item.setExpirationDate(this.expirationDate);
        item.setImageUrl(this.imageUrl);
        item.setInitialValue(this.initialValue);
        item.setUserId(this.userId);
        return item;
    }

}
