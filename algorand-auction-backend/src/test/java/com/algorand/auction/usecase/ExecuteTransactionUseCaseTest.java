package com.algorand.auction.usecase;

import com.algorand.auction.model.User;
import com.algorand.auction.usecase.repository.TransactionRepository;
import com.algorand.auction.usecase.repository.UserRepository;
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
        User receiverUser = new User();
        receiverUser.setPublicKey(RECEIVER_PUBLIC_KEY);

        when(userRepository.getPublicKeyFor(senderUserId)).thenReturn(SENDER_PUBLIC_KEY);

        underTest.execute(senderUserId, receiverUser, notes);

        verify(transactionRepository).saveTransaction(SENDER_PUBLIC_KEY, RECEIVER_PUBLIC_KEY, notes);
    }
}