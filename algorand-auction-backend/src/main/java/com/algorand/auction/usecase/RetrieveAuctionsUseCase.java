package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;
import com.algorand.auction.model.Item;
import com.algorand.auction.usecase.repository.AuctionRepository;
import com.algorand.auction.usecase.repository.BidRepository;

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

    public List<Item> retrieveAll() {
        return auctionRepository.retrieveAll();
    }

    public Auction retrieveBy(Integer auctionId) {
        Item item = auctionRepository.retrieveBy(auctionId);
        List<Bid> bids = bidRepository.getAllBidsFor(auctionId);
        return createAuction(item, bids);
    }

    private Auction createAuction(Item item, List<Bid> bids) {
        Auction auction = new Auction();
        auction.setItem(item);
        auction.setBids(bids);
        return auction;
    }

}
