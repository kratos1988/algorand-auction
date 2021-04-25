package com.algorand.auction.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ExecuteTransactionUseCaseTest {

    public static final String SENDER_PUBLIC_KEY = "Sender Public key";
    public static final String RECEIVER_PUBLIC_KEY = "Receiver public key";
    private ExecuteTransactionUseCase underTest;

    private TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        underTest = new ExecuteTransactionUseCase(transactionRepository, userRepository);
    }

    @Test
    void executeTransaction() throws Exception {
        String notes = "some notes";

        String senderUserId= "SENDER_USER_ID";
        String receiverUserId= "RECEIVER_USER_ID";

        when(userRepository.getPublicKeyFor(senderUserId)).thenReturn(SENDER_PUBLIC_KEY);
        when(userRepository.getPublicKeyFor(receiverUserId)).thenReturn(RECEIVER_PUBLIC_KEY);

        underTest.execute(senderUserId, receiverUserId, notes);

        verify(transactionRepository).saveTransaction(SENDER_PUBLIC_KEY, RECEIVER_PUBLIC_KEY, notes);
    }
}