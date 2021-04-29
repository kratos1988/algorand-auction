package com.algorand.auction.usecase.repository;

import com.algorand.auction.model.Bid;
import com.algorand.auction.usecase.SaveBidRequest;

import java.util.List;

public interface BidRepository {

    void saveBid(SaveBidRequest saveBidRequest);
    Bid getHighestBidFor(int anAuctionId);
    List<Bid> getAllBidsFor(int auctionId);
}
