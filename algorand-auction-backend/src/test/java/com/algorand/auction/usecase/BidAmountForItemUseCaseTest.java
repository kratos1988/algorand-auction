package com.algorand.auction.usecase;

import com.algorand.auction.model.Bid;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.algorand.auction.model.BidBuilder.aBid;
import static java.math.BigDecimal.TEN;
import static org.mockito.Mockito.*;

class BidAmountForItemUseCaseTest {

    private BidRepository bidRepository = mock(BidRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private BidAmountForItemUseCase underTest;

    @BeforeEach
    void setUp() {
        underTest = new BidAmountForItemUseCase(bidRepository, userRepository);
    }

    @Test
    void bid() {
        int auctionId = 1;
        int userId = 100;
        String userName = "AN_USER_NAME";
        BigDecimal amount = new BigDecimal("11");
        Bid bid = aBid().withAmount(TEN).build();

        SaveBidRequest expectedRequest = new SaveBidRequest(auctionId, amount, userId);

        when(bidRepository.getHighestBidFor(auctionId)).thenReturn(bid);
        when(userRepository.getIdByUsername(userName)).thenReturn(userId);

        underTest.bid(amount, auctionId, userName);

        verify(bidRepository, times(1)).saveBid(expectedRequest);
    }

    @Test
    void noBidWhenAmountIsLess() {
        int auctionId = 1;
        int userId = 100;
        String userName = "AN_USER_NAME";
        BigDecimal amount = new BigDecimal("9");
        Bid bid = aBid().withAmount(amount).build();
        SaveBidRequest expectedRequest = new SaveBidRequest(auctionId, amount, userId);


        when(bidRepository.getHighestBidFor(auctionId)).thenReturn(bid);
        when(userRepository.getIdByUsername(userName)).thenReturn(userId);

        underTest.bid(amount, auctionId, userName);

        verify(bidRepository, never()).saveBid(expectedRequest);
    }

}