package com.algorand.auction.usecase;

import com.algorand.auction.model.Bid;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.TransactionRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.algorand.auction.model.BidBuilder.aBid;
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
        Bid bid = aBid().withAmount(TEN).build();

        SaveBidRequest expectedRequest = new SaveBidRequest(auctionId, amount, userId);

        BidAmountForItemUseCase underTest = new BidAmountForItemUseCase(bidRepository);

        when(bidRepository.getHighestBidFor(auctionId)).thenReturn(bid);

        underTest.bid(amount, auctionId, userId);

        verify(bidRepository, times(1)).saveBid(expectedRequest);
    }

    @Test
    void noBidWhenAmountIsLess() {
        int auctionId = 1;
        String userId = "AN_USER_ID";
        BigDecimal amount = new BigDecimal("9");
        Bid bid = aBid().withAmount(amount).build();
        SaveBidRequest expectedRequest = new SaveBidRequest(auctionId, amount, userId);

        BidAmountForItemUseCase underTest = new BidAmountForItemUseCase(bidRepository);

        when(bidRepository.getHighestBidFor(auctionId)).thenReturn(bid);

        underTest.bid(amount, auctionId, userId);

        verify(bidRepository, never()).saveBid(expectedRequest);
    }

}