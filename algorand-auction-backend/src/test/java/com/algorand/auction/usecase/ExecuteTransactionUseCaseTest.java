package com.algorand.auction.usecase;

import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;
import com.algorand.auction.usecase.repository.TransactionRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.algorand.auction.model.TransactionBuilder.aTransaction;
import static java.math.BigDecimal.TEN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ExecuteTransactionUseCaseTest {

    public static final String SENDER_PUBLIC_KEY = "Sender Public key";
    public static final String RECEIVER_PUBLIC_KEY = "Receiver public key";
    private ExecuteTransactionUseCase underTest;

    private TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        underTest = new ExecuteTransactionUseCase(transactionRepository);
    }

    @Test
    void executeTransaction() throws Exception {
        BigDecimal amount = TEN;
        User buyer = new User();
        buyer.setPublicKey(SENDER_PUBLIC_KEY);
        User seller = new User();
        seller.setPublicKey(RECEIVER_PUBLIC_KEY);

        Transaction aTransaction =
                aTransaction()
                        .withAmount(amount)
                        .withSeller(seller)
                        .withBuyer(buyer)
                        .build();

        underTest.execute(aTransaction);

        verify(transactionRepository).saveTransaction(SENDER_PUBLIC_KEY, RECEIVER_PUBLIC_KEY, amount, "");
    }
}