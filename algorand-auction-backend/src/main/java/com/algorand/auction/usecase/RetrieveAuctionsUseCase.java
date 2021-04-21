package com.algorand.auction.usecase;

import com.algorand.auction.jdbc.AuctionDto;
import com.algorand.auction.jdbc.AuctionDtoToDomainConverter;
import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toUnmodifiableList;

public class RetrieveAuctionsUseCase {

    private AuctionRepository auctionRepository;
    private BidRepository bidRepository;
    private AuctionDtoToDomainConverter converter;

    public RetrieveAuctionsUseCase(
            AuctionRepository auctionRepository,
            BidRepository bidRepository,
            AuctionDtoToDomainConverter converter
    ) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
        this.converter = converter;
    }

    public List<Auction> retrieveAll() {
        return auctionRepository.retrieveAll().stream()
                .map(auctionDto -> convertToAuction(auctionDto))
                .collect(toUnmodifiableList());
    }

    public Auction retrieveBy(Integer id) {
        AuctionDto auctionDto = auctionRepository.retrieveBy(id);
        List<Bid> bids = bidRepository.getAllBidsFor(id);
        return converter.apply(auctionDto, bidRepository.getHighestBidFor(auctionDto.id), bids);
    }

    private Auction convertToAuction(AuctionDto auctionDto) {
        return converter.apply(auctionDto, bidRepository.getHighestBidFor(auctionDto.id), emptyList());
    }
}
