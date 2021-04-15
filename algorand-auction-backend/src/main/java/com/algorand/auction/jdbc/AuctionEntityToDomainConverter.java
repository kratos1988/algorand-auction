package com.algorand.auction.jdbc;

import com.algorand.auction.model.Auction;

import java.util.function.Function;

public class AuctionEntityToDomainConverter implements Function<AuctionDto, Auction> {

    @Override
    public Auction apply(AuctionDto auctionDto) {
        Auction auction = new Auction();
        auction.setName(auctionDto.name);
        return auction;
    }
}
