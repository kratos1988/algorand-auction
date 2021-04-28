package com.algorand.auction.usecase;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.algorand.auction.model.AuctionBuilder.anAuction;
import static com.algorand.auction.model.BidBuilder.aBid;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RetrieveAuctionsUseCaseTest {

    AuctionRepository auctionRepository = mock(AuctionRepository.class);
    BidRepository bidRepository = mock(BidRepository.class);
    private RetrieveAuctionsUseCase underTest;

    @BeforeEach
    void setUp() {
        underTest = new RetrieveAuctionsUseCase(auctionRepository, bidRepository);
    }

    @Test
    void retrieveAllAuctions() {
        LocalDateTime now = LocalDateTime.now();
        Auction anAuction = anAuction()
                .withId(1)
                .withInitialValue(new BigDecimal("20.99"))
                .withItemName("AN_ITEM_NAME")
                .withDescription("AN_ITEM_DESCRIPTION")
                .withTitle("A_TITLE")
                .withExpirationDate(now)
                .withImageUrl("AN_IMAGE_URL")
                .withHighestBid(new BigDecimal("22.89"))
                .build();

        when(auctionRepository.retrieveAll()).thenReturn(singletonList(anAuction));

        List<Auction> retrievedAuctions = underTest.retrieveAll();
        assertThat(retrievedAuctions, hasSize(1));
        Auction auction = retrievedAuctions.get(0);
        assertThat(auction.getId(), equalTo(1));
        assertThat(auction.getInitialValue(), equalTo(new BigDecimal("20.99")));
        assertThat(auction.getItemName(), equalTo("AN_ITEM_NAME"));
        assertThat(auction.getDescription(), equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(auction.getTitle(), equalTo("A_TITLE"));
        assertThat(auction.getExpirationDate(), equalTo(now));
        assertThat(auction.getImageUrl(), equalTo("AN_IMAGE_URL"));
        assertThat(auction.getHighestBid(), equalTo(new BigDecimal("22.89")));
    }

    @Test
    void retrieveAuction() {

        LocalDateTime now = LocalDateTime.now();
        Auction anAuction = anAuction()
                .withId(1)
                .withInitialValue(new BigDecimal("20.99"))
                .withItemName("AN_ITEM_NAME")
                .withDescription("AN_ITEM_DESCRIPTION")
                .withTitle("A_TITLE")
                .withExpirationDate(now)
                .withImageUrl("AN_IMAGE_URL")
                .withHighestBid(new BigDecimal("22.89"))
                .build();
        Bid aBid = aBid().build();
        Bid anotherBid = aBid().build();


        when(auctionRepository.retrieveBy(1)).thenReturn(anAuction);
        when(bidRepository.getAllBidsFor(1)).thenReturn(asList(aBid, anotherBid));

        Auction retrievedAuction = underTest.retrieveBy(1);

        assertThat(retrievedAuction.getId(), equalTo(1));
        assertThat(retrievedAuction.getInitialValue(), equalTo(new BigDecimal("20.99")));
        assertThat(retrievedAuction.getItemName(), equalTo("AN_ITEM_NAME"));
        assertThat(retrievedAuction.getDescription(), equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(retrievedAuction.getTitle(), equalTo("A_TITLE"));
        assertThat(retrievedAuction.getExpirationDate(), equalTo(now));
        assertThat(retrievedAuction.getImageUrl(), equalTo("AN_IMAGE_URL"));
        assertThat(retrievedAuction.getHighestBid(), equalTo(new BigDecimal("22.89")));
        assertThat(retrievedAuction.getBids(), containsInAnyOrder(aBid, anotherBid));
    }
}