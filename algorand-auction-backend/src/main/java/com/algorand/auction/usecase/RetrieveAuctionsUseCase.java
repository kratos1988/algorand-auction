package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Item;
import com.algorand.auction.usecase.repository.AuctionRepository;
import com.algorand.auction.usecase.repository.BidRepository;
import io.vavr.control.Either;

import java.util.List;

import static io.vavr.control.Either.right;

public class RetrieveAuctionsUseCase {

    private AuctionRepository auctionRepository;
    private BidRepository bidRepository;

    public RetrieveAuctionsUseCase(
            AuctionRepository auctionRepository,
            BidRepository bidRepository
    ) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    public Either<FailureError, List<Item>> retrieveAll() {
        return auctionRepository.retrieveAll();
    }

    public Either<FailureError, Auction> retrieveById(Integer auctionId) {
        return auctionRepository.retrieveBy(auctionId)
                .flatMap(auction ->
                        bidRepository.getAllBidsFor(auctionId)
                                .flatMap(bids -> right(createAuction(auction, bids)))
                );
    }

    private Auction createAuction(Item item, List<Bid> bids) {
        Auction auction = new Auction();
        auction.setItem(item);
        auction.setBids(bids);
        return auction;
    }

}
