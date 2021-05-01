package com.algorand.auction.usecase;

import com.algorand.auction.model.Bid;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.UserRepository;

import java.math.BigDecimal;

public class BidAmountForItemUseCase {
    private final BidRepository bidRepository;
    private final UserRepository userRepository;

    public BidAmountForItemUseCase(
            BidRepository bidRepository, UserRepository userRepository) {

        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
    }

    public void bid(BigDecimal amount, int auctionId, String userName) {
        Bid highestBid = bidRepository.getHighestBidFor(auctionId);
        if(highestBid.getAmount().compareTo(amount) < 0) {
            int userId = userRepository.getIdByUsername(userName);
            bidRepository.saveBid(amount, userId, auctionId);
        }
    }
}
