package com.algorand.auction.usecase;

import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.vavr.control.Either.right;
import static java.math.BigDecimal.TEN;
import static org.mockito.Mockito.*;

class BidAmountForItemUseCaseTest {

    private BidRepository bidRepository = mock(BidRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private UserTokenRetriever userTokenRetriever = mock(UserTokenRetriever.class);
    private BidAmountForItemUseCase underTest;

    @BeforeEach
    void setUp() {
        underTest = new BidAmountForItemUseCase(bidRepository, userRepository, userTokenRetriever);
    }

    @Test
    void bid() {
        int auctionId = 1;
        int userId = 100;
        String userName = "AN_USER_NAME";
        String token = "A_TOKEN";
        BigDecimal amount = new BigDecimal("11");


        when(bidRepository.getHighestBidAmountFor(auctionId)).thenReturn(right(TEN));
        when(userRepository.getIdByUsername(userName)).thenReturn(right(userId));
        when(userTokenRetriever.getUserNameBy(token)).thenReturn(right(userName));

        underTest.bid(amount, auctionId, token);

        verify(bidRepository, times(1)).saveBid(amount, userId, auctionId);
    }

    @Test
    void noBidWhenAmountIsLess() {
        int auctionId = 1;
        int userId = 100;
        String userName = "AN_USER_NAME";
        BigDecimal amount = new BigDecimal("11");


        when(bidRepository.getHighestBidAmountFor(auctionId)).thenReturn(right(amount));

        underTest.bid(amount, auctionId, userName);

        verify(bidRepository, never()).saveBid(TEN, userId, auctionId);
        verifyNoInteractions(userRepository);
    }

}