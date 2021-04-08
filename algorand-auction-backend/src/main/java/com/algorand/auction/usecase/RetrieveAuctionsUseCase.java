package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;

import java.util.List;

public class RetrieveAuctionsUseCase {

    private AuctionRepository auctionRepository;

    public RetrieveAuctionsUseCase(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public List<Auction> retrieveAll() {
        return auctionRepository.retrieveAll();
    }
}
