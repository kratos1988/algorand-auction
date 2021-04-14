package com.algorand.auction.usecase;

import java.math.BigDecimal;

public interface BidRepository {

    void saveBid(SaveBidRequest saveBidRequest);
    BigDecimal getHighestBidFor(int anAuctionId);
}
