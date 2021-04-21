package com.algorand.auction.usecase;

import com.algorand.auction.jdbc.AuctionDto;

import java.util.List;

public interface AuctionRepository {

    List<AuctionDto> retrieveAll();

    AuctionDto retrieveBy(Integer id);
}
