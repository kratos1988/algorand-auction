package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.AuctionBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RetrieveAuctionsUseCaseTest {

    AuctionRepository auctionRepository = mock(AuctionRepository.class);

    @Test
    void retrieve() {
        RetrieveAuctionsUseCase useCase = new RetrieveAuctionsUseCase(auctionRepository);

        when(auctionRepository.retrieveAll()).thenReturn(singletonList(new AuctionBuilder().build()));
        List<Auction> retrievedAuctions = useCase.retrieveAll();

        assertTrue(retrievedAuctions.size() > 0);
    }
}