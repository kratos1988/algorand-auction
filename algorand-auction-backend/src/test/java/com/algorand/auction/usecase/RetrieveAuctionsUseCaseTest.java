package com.algorand.auction.usecase;

import com.algorand.auction.jdbc.AuctionDto;
import com.algorand.auction.jdbc.AuctionDtoToDomainConverter;
import com.algorand.auction.model.Auction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.algorand.auction.jdbc.AuctionDtoBuilder.anAuctionDto;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RetrieveAuctionsUseCaseTest {

    AuctionRepository auctionRepository = mock(AuctionRepository.class);
    BidRepository bidRepository = mock(BidRepository.class);

    @Test
    void retrieve() {
        RetrieveAuctionsUseCase useCase = new RetrieveAuctionsUseCase(auctionRepository, bidRepository, new AuctionDtoToDomainConverter());

        AuctionDto anAuction = anAuctionDto()
                .withId(1)
                .withInitialValue(new BigDecimal("20.99"))
                .withItemName("AN_ITEM_NAME")
                .withItemDescription("AN_ITEM_DESCRIPTION")
                .build();

        when(auctionRepository.retrieveAll()).thenReturn(singletonList(anAuction));
        when(bidRepository.getHighestBidFor(1)).thenReturn(new BigDecimal("22.89"));

        List<Auction> retrievedAuctions = useCase.retrieveAll();

        assertThat(retrievedAuctions, hasSize(1));
        Auction auction = retrievedAuctions.get(0);
        assertThat(auction.getId(), equalTo(1));
        assertThat(auction.getInitialValue(), equalTo(new BigDecimal("20.99")));
        assertThat(auction.getItemName(), equalTo("AN_ITEM_NAME"));
        assertThat(auction.getItemDescription(), equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(auction.getHighestBid(), equalTo(new BigDecimal("22.89")));
    }
}