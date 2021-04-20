package com.algorand.auction.usecase;

import com.algorand.auction.jdbc.AuctionDto;
import com.algorand.auction.jdbc.AuctionDtoToDomainConverter;
import com.algorand.auction.model.Auction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.algorand.auction.jdbc.AuctionDtoBuilder.anAuctionDto;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RetrieveAuctionsUseCaseTest {

    AuctionRepository auctionRepository = mock(AuctionRepository.class);
    BidRepository bidRepository = mock(BidRepository.class);

    @Test
    void retrieve() {
        RetrieveAuctionsUseCase useCase = new RetrieveAuctionsUseCase(auctionRepository, bidRepository, new AuctionDtoToDomainConverter());

        AuctionDto anAuction = anAuctionDto().build();

        when(auctionRepository.retrieveAll()).thenReturn(singletonList(anAuction));
        List<Auction> retrievedAuctions = useCase.retrieveAll();

        assertThat(retrievedAuctions, hasSize(1));
        //TODO assertion sui campi
    }
}