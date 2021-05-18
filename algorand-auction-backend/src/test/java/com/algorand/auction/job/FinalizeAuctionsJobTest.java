package com.algorand.auction.job;

import com.algorand.auction.model.Bid;
import com.algorand.auction.model.Item;
import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;
import com.algorand.auction.usecase.ExecuteTransactionUseCase;
import com.algorand.auction.usecase.repository.BidRepository;
import com.algorand.auction.usecase.repository.ItemRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.algorand.auction.model.BidBuilder.aBid;
import static com.algorand.auction.model.ItemBuilder.anItem;
import static com.algorand.auction.model.TransactionBuilder.aTransaction;
import static com.algorand.auction.model.UserBuilder.anUser;
import static io.vavr.control.Either.right;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

class FinalizeAuctionsJobTest {

    private ExecuteTransactionUseCase useCase = mock(ExecuteTransactionUseCase.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private BidRepository bidRepository = mock(BidRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private FinalizeAuctionsJob underTest;

    @BeforeEach
    void setUp() {
        underTest = new FinalizeAuctionsJob(useCase, itemRepository, bidRepository, userRepository);
    }

    @Test
    void finalizeAuctions() {
        Item firstExpired = anItem().withId(1).withUserId(3).build();
        BigDecimal firstAmount = new BigDecimal("10.99");
        Bid firstHighestBid = aBid().withUserId(1).withAmount(firstAmount).build();
        User firstBuyer = anUser().withUserName("FIRST_USER").build();
        User firstSeller = anUser().withUserName("THIRD_USER").build();

        Item secondExpired = anItem().withId(2).withUserId(4).build();
        BigDecimal secondAmount = new BigDecimal("10.99");
        Bid secondHighestBid = aBid().withUserId(2).withAmount(secondAmount).build();
        User secondBuyer = anUser().withUserName("SECOND_USER").build();
        User secondSeller = anUser().withUserName("FOURTH_USER").build();

        Transaction expectedFirstTransaction =
                aTransaction()
                        .withBuyer(firstBuyer)
                        .withSeller(firstSeller)
                        .withAmount(firstAmount)
                        .build();

        Transaction expectedSecondTransaction =
                aTransaction()
                        .withBuyer(secondBuyer)
                        .withSeller(secondSeller)
                        .withAmount(secondAmount)
                        .build();

        when(itemRepository.retrieveExpired()).thenReturn(right(asList(firstExpired, secondExpired)));
        when(bidRepository.getHighestBidFor(1)).thenReturn(right(firstHighestBid));
        when(bidRepository.getHighestBidFor(2)).thenReturn(right(secondHighestBid));
        when(userRepository.getUserById(1)).thenReturn(right(firstBuyer));
        when(userRepository.getUserById(3)).thenReturn(right(firstSeller));
        when(userRepository.getUserById(2)).thenReturn(right(secondBuyer));
        when(userRepository.getUserById(4)).thenReturn(right(secondSeller));
        when(itemRepository.setStatusFinished(asList(1, 2))).thenReturn(right(null));
        when(useCase.execute(any())).thenReturn(right(""));

        underTest.apply();

        verify(itemRepository).retrieveExpired();
        verify(bidRepository).getHighestBidFor(1);
        verify(bidRepository).getHighestBidFor(2);
        verify(userRepository).getUserById(1);
        verify(userRepository).getUserById(3);
        verify(userRepository).getUserById(2);
        verify(userRepository).getUserById(4);
        verify(useCase, times(1)).execute(eq(expectedFirstTransaction));
        verify(useCase, times(1)).execute(eq(expectedSecondTransaction));
        verify(itemRepository, times(1)).setStatusFinished(asList(1, 2));

    }
}