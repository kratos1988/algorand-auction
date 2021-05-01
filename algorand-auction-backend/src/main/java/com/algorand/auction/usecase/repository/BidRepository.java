package com.algorand.auction.usecase.repository;

import com.algorand.auction.model.Bid;
import com.algorand.auction.model.FailureError;
import io.vavr.control.Either;

import java.math.BigDecimal;
import java.util.List;

public interface BidRepository {

    void saveBid(BigDecimal amount, int userId, int auctionId);
    Bid getHighestBidFor(int anAuctionId);
    Either<FailureError,List<Bid>> getAllBidsFor(int auctionId);
}
