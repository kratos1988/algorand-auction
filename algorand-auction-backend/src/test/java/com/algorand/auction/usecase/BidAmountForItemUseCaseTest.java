package com.algorand.auction.usecase;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static org.mockito.Mockito.*;

class BidAmountForItemUseCaseTest {

    private BidRepository bidRepository = mock(BidRepository.class);
    private TransactionRepository transactionRepository = mock(TransactionRepository.class);

    @Test
    void bid() {
        int auctionId = 1;
        String userId = "AN_USER_ID";
        BigDecimal amount = new BigDecimal("11");
        SaveBidRequest expectedRequest = new SaveBidRequest(auctionId, amount, userId);

        BidAmountForItemUseCase underTest = new BidAmountForItemUseCase(bidRepository);

        when(bidRepository.getHighestBidFor(auctionId)).thenReturn(TEN);

        underTest.bid(amount, auctionId, userId);

        verify(bidRepository, times(1)).saveBid(expectedRequest);
    }

    @Test
    void noBidWhenAmountIsLess() {
        int auctionId = 1;
        String userId = "AN_USER_ID";
        BigDecimal amount = new BigDecimal("9");
        SaveBidRequest expectedRequest = new SaveBidRequest(auctionId, amount, userId);

        BidAmountForItemUseCase underTest = new BidAmountForItemUseCase(bidRepository);

        when(bidRepository.getHighestBidFor(auctionId)).thenReturn(TEN);

        underTest.bid(amount, auctionId, userId);

        verify(bidRepository, never()).saveBid(expectedRequest);
    }

}