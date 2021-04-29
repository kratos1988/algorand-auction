package com.algorand.auction.usecase.repository;

import com.algorand.auction.model.Item;

import java.util.List;

public interface AuctionRepository {

    List<Item> retrieveAll();

    Item retrieveBy(Integer id);

    List<Item> retrieveExpired();
}
