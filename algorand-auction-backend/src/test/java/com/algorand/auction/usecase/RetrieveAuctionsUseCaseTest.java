package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.AuctionBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RetrieveAuctionsUseCaseTest {

    AuctionRepository auctionRepository = mock(AuctionRepository.class);

    @Test
    void retrieve() {
        RetrieveAuctionsUseCase useCase = new RetrieveAuctionsUseCase(auctionRepository);

        Auction anAuction = new AuctionBuilder().build();

        when(auctionRepository.retrieveAll()).thenReturn(singletonList(anAuction));
        List<Auction> retrievedAuctions = useCase.retrieveAll();

        assertThat(retrievedAuctions, hasSize(1));
        assertThat(retrievedAuctions.get(0), is(equalTo(anAuction)));
    }
}