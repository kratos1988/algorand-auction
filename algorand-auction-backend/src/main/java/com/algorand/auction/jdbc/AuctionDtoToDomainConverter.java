package com.algorand.auction.jdbc;

import com.algorand.auction.model.Auction;

import java.math.BigDecimal;

public class AuctionDtoToDomainConverter {

    public Auction apply(AuctionDto auctionDto, BigDecimal highestBidFor) {
        Auction auction = new Auction();
        auction.setItemName(auctionDto.itemName);
        auction.setItemDescription(auctionDto.itemDescription);
        auction.setInitialValue(auctionDto.initialValue);
        return auction;
    }
}
