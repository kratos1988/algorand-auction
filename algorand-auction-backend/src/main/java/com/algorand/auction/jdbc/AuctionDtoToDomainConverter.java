package com.algorand.auction.jdbc;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;

import java.math.BigDecimal;
import java.util.List;

public class AuctionDtoToDomainConverter {

    public Auction apply(AuctionDto auctionDto, BigDecimal highestBid, List<Bid> bids) {
        Auction auction = new Auction();
        auction.setId(auctionDto.id);
        auction.setItemName(auctionDto.itemName);
        auction.setDescription(auctionDto.description);
        auction.setTitle(auctionDto.title);
        auction.setExpirationDate(auctionDto.expirationDate);
        auction.setImageUrl(auctionDto.imageUrl);
        auction.setInitialValue(auctionDto.initialValue);
        auction.setHighestBid(highestBid);
        auction.setBids(bids);
        return auction;
    }
}
