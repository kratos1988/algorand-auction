package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Item;
import com.algorand.auction.usecase.repository.AuctionRepository;
import com.algorand.auction.usecase.repository.BidRepository;
import io.vavr.Tuple;
import io.vavr.Tuple2;
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
                .flatMap(auction -> getAllBids(auctionId, auction))
                .flatMap(input -> right(createAuction(input._1, input._2)));
    }

    private Either<FailureError, Tuple2<Item, List<Bid>>> getAllBids(Integer auctionId, Item auction) {
        return bidRepository.getAllBidsFor(auctionId).map(bids -> Tuple.of(auction, bids));
    }

    private Auction createAuction(Item item, List<Bid> bids) {
        Auction auction = new Auction();
        auction.setItem(item);
        auction.setBids(bids);
        return auction;
    }

}
