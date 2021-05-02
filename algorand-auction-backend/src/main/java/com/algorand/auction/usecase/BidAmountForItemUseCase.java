package com.algorand.auction.usecase;

import com.algorand.auction.model.Bid;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import io.vavr.control.Either;

import java.math.BigDecimal;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class BidAmountForItemUseCase {
    private final BidRepository bidRepository;
    private final UserRepository userRepository;

    public BidAmountForItemUseCase(
            BidRepository bidRepository, UserRepository userRepository) {

        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
    }

    public Either<FailureError, Void> bid(BigDecimal amount, int auctionId, String userName) {
        return bidRepository.getHighestBidFor(auctionId)
                .flatMap(highestBid -> checkBidIsValid(amount, auctionId, userName, highestBid))
                .flatMap(x ->  userRepository.getIdByUsername(userName))
                .flatMap(userId -> bidRepository.saveBid(amount, userId, auctionId));
    }

    private Either<FailureError, Void> checkBidIsValid(BigDecimal amount, int auctionId, String userName, Bid highestBid) {
        if(highestBid.getAmount().compareTo(amount) >= 0) {
            return left(new BidAmountLessThanHighest(userName, auctionId, amount));
        }
        return right(null);
    }
}
