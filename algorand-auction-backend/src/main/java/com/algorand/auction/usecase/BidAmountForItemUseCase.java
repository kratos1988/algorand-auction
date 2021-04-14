package com.algorand.auction.usecase;

import java.math.BigDecimal;

public class BidAmountForItemUseCase {
    private final BidRepository bidRepository;

    public BidAmountForItemUseCase(
            BidRepository bidRepository) {

        this.bidRepository = bidRepository;
    }

    public void bid(BigDecimal amount, int auctionId, String userId) {
        BigDecimal highestBid = bidRepository.getHighestBidFor(auctionId);
        if(highestBid.compareTo(amount) < 0) {
            bidRepository.saveBid(new SaveBidRequest(auctionId, amount, userId));
        }
    }
}
