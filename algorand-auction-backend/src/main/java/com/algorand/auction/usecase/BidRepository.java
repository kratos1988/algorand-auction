package com.algorand.auction.usecase;

import com.algorand.auction.model.Bid;

import java.math.BigDecimal;
import java.util.List;

public interface BidRepository {

    void saveBid(SaveBidRequest saveBidRequest);
    BigDecimal getHighestBidFor(int anAuctionId);
    List<Bid> getAllBidsFor(int auctionId);
}
