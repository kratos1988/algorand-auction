package com.algorand.auction.usecase;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.usecase.error.BidAmountLessThanHighestError;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class BidAmountForItemUseCase {

    private final Logger logger = LoggerFactory.getLogger(BidAmountForItemUseCase.class);

    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final UserTokenRetriever userTokenRetriever;

    public BidAmountForItemUseCase(
            BidRepository bidRepository, UserRepository userRepository, UserTokenRetriever userTokenRetriever) {

        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
        this.userTokenRetriever = userTokenRetriever;
    }

    public Either<FailureError, Void> bid(BigDecimal amount, int auctionId, String userToken) {
        return bidRepository.getHighestBidAmountFor(auctionId)
                .flatMap(highestBid -> checkBidIsValid(amount, auctionId, userToken, highestBid))
                .flatMap(result -> userTokenRetriever.getUserNameBy(userToken))
                .flatMap(userRepository::getIdByUsername)
                .flatMap(userId -> bidRepository.saveBid(amount, userId, auctionId));
    }

    private Either<FailureError, Void> checkBidIsValid(BigDecimal amount, int auctionId, String userName, BigDecimal highestBid) {
        if(highestBid.compareTo(amount) >= 0) {
            logger.error("The amount: {} is less than higher: {} for: {}", amount, highestBid, auctionId);
            return left(new BidAmountLessThanHighestError(userName, auctionId, amount));
        }
        return right(null);
    }
}
