package com.algorand.auction.rest;

import com.algorand.auction.model.Auction;

import java.util.function.Function;

public class DomainToDtoAuctionConverter implements Function<Auction, AuctionDto> {

    @Override
    public AuctionDto apply(Auction auction) {
        return new AuctionDto(auction.getName());
    }
}
