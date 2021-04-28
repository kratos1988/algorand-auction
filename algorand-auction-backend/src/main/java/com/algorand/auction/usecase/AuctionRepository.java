package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.ExpiredAuction;

import java.util.List;

public interface AuctionRepository {

    List<Auction> retrieveAll();

    Auction retrieveBy(Integer id);

    List<ExpiredAuction> retrieveExpired();
}
