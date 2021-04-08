package com.algorand.auction.rest;

import com.algorand.auction.jdbc.AuctionDto;
import com.algorand.auction.model.Auction;

import java.util.function.Function;

public class DtoToDomainAuctionConverter implements Function<AuctionDto, Auction> {

    @Override
    public Auction apply(AuctionDto auctionDto) {
        Auction auction = new Auction();
        auction.setName(auctionDto.name);
        return auction;
    }
}
