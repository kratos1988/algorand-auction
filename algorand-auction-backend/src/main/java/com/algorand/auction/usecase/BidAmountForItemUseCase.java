package com.algorand.auction.usecase;

import com.algorand.auction.model.Bid;
import com.algorand.auction.usecase.repository.BidRepository;

import java.math.BigDecimal;

public class BidAmountForItemUseCase {
    private final BidRepository bidRepository;

    public BidAmountForItemUseCase(
            BidRepository bidRepository) {

        this.bidRepository = bidRepository;
    }

    public void bid(BigDecimal amount, int auctionId, String userId) {
        Bid highestBid = bidRepository.getHighestBidFor(auctionId);
        if(highestBid.getAmount().compareTo(amount) < 0) {
            bidRepository.saveBid(new SaveBidRequest(auctionId, amount, userId));
        }
    }
}
