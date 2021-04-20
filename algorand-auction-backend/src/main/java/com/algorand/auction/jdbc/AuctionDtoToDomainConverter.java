package com.algorand.auction.jdbc;

import com.algorand.auction.model.Auction;

import java.math.BigDecimal;

public class AuctionDtoToDomainConverter {

    public Auction apply(AuctionDto auctionDto, BigDecimal highestBid) {
        Auction auction = new Auction();
        auction.setId(auctionDto.id);
        auction.setItemName(auctionDto.itemName);
        auction.setItemDescription(auctionDto.itemDescription);
        auction.setInitialValue(auctionDto.initialValue);
        auction.setHighestBid(highestBid);
        return auction;
    }
}
