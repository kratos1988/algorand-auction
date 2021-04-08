package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;

import java.util.List;

public interface AuctionRepository {

    List<Auction> retrieveAll();
}
