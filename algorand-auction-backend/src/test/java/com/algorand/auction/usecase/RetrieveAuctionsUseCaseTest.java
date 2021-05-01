package com.algorand.auction.usecase;

import com.algorand.auction.jdbc.DatabaseError;
import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Item;
import com.algorand.auction.usecase.repository.AuctionRepository;
import com.algorand.auction.usecase.repository.BidRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.algorand.auction.model.BidBuilder.aBid;
import static com.algorand.auction.model.ItemBuilder.anItem;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Item anItem = anItem()
                .withId(1)
                .withInitialValue(new BigDecimal("20.99"))
                .withItemName("AN_ITEM_NAME")
                .withDescription("AN_ITEM_DESCRIPTION")
                .withTitle("A_TITLE")
                .withExpirationDate(now)
                .withImageUrl("AN_IMAGE_URL")
                .withHighestBid(new BigDecimal("22.89"))
                .build();

        when(auctionRepository.retrieveAll()).thenReturn(right(singletonList(anItem)));
        Either<FailureError, List<Item>> result = underTest.retrieveAll();
        assertTrue(result.isRight());
        List<Item> retrievedItems = result.get();

        assertThat(retrievedItems, hasSize(1));
        Item item = retrievedItems.get(0);
        assertThat(item.getId(), equalTo(1));
        assertThat(item.getInitialValue(), equalTo(new BigDecimal("20.99")));
        assertThat(item.getItemName(), equalTo("AN_ITEM_NAME"));
        assertThat(item.getDescription(), equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(item.getTitle(), equalTo("A_TITLE"));
        assertThat(item.getExpirationDate(), equalTo(now));
        assertThat(item.getImageUrl(), equalTo("AN_IMAGE_URL"));
        assertThat(item.getHighestBid(), equalTo(new BigDecimal("22.89")));
    }

    @Test
    void whenErrorRetrievingAuctions() {


        DatabaseError error = new DatabaseError(new RuntimeException("an Error"));
        when(auctionRepository.retrieveAll()).thenReturn(left(error));

        Either<FailureError, List<Item>> result = underTest.retrieveAll();
        assertTrue(result.isLeft());


        assertThat(result.getLeft(), equalTo(error));
    }

    @Test
    void retrieveAuction() {

        Item anItem = anItem()
                .withId(1)
                .build();
        Bid aBid = aBid().build();
        Bid anotherBid = aBid().build();


        when(auctionRepository.retrieveBy(1)).thenReturn(right(anItem));
        when(bidRepository.getAllBidsFor(1)).thenReturn(right(asList(aBid, anotherBid)));

        Either<FailureError, Auction> result = underTest.retrieveById(1);
        assertTrue(result.isRight());
        Auction auction = result.get();

        assertThat(auction.getItem(), equalTo(anItem));
        assertThat(auction.getBids(), containsInAnyOrder(aBid, anotherBid));
    }

    @Test
    void whenErrorRetrievingAuction() {


        DatabaseError error = new DatabaseError(new RuntimeException("an Error"));
        when(auctionRepository.retrieveBy(1)).thenReturn(left(error));

        Either<FailureError, Auction> result = underTest.retrieveById(1);
        assertTrue(result.isLeft());


        assertThat(result.getLeft(), equalTo(error));
    }
}