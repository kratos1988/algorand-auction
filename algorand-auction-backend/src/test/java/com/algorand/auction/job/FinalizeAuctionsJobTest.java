package com.algorand.auction.job;

import com.algorand.auction.model.Bid;
import com.algorand.auction.model.Item;
import com.algorand.auction.usecase.ExecuteTransactionUseCase;
import com.algorand.auction.usecase.repository.AuctionRepository;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.algorand.auction.model.BidBuilder.aBid;
import static com.algorand.auction.model.ItemBuilder.anItem;
import static io.vavr.control.Either.right;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FinalizeAuctionsJobTest {

    private ExecuteTransactionUseCase useCase = mock(ExecuteTransactionUseCase.class);
    private AuctionRepository auctionRepository = mock(AuctionRepository.class);
    private BidRepository bidRepository = mock(BidRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private FinalizeAuctionsJob underTest;

    @BeforeEach
    void setUp() {
        underTest = new FinalizeAuctionsJob(useCase, auctionRepository, bidRepository, userRepository);
    }

    @Test
    void finalizeAuctions() {
        Item firstExpired = anItem().withId(1).build();
        Bid firstHighestBid = aBid().build();
//        User firstBuyer = anUser().build();
        Item secondExpired = anItem().withId(2).build();
        Bid secondHighestBid = aBid().build();

        when(auctionRepository.retrieveExpired()).thenReturn(right(asList(firstExpired, secondExpired)));
        when(bidRepository.getHighestBidFor(1)).thenReturn(right(firstHighestBid));
        when(bidRepository.getHighestBidFor(2)).thenReturn(right(secondHighestBid));

        underTest.apply();
    }
}