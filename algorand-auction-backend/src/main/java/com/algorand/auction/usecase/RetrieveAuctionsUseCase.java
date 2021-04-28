package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;

import java.util.List;

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

    public List<Auction> retrieveAll() {
        return auctionRepository.retrieveAll();
    }

    public Auction retrieveBy(Integer auctionId) {
        Auction auction = auctionRepository.retrieveBy(auctionId);
        List<Bid> bids = bidRepository.getAllBidsFor(auctionId);
        auction.setBids(bids);
        return auction;
    }

}
