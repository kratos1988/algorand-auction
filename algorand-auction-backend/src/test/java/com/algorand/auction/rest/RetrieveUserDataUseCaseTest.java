package com.algorand.auction.rest;

import com.algorand.auction.jdbc.NoRecordError;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;
import com.algorand.auction.rest.request.CredentialsRequest;
import com.algorand.auction.rest.response.AuthenticationResponse;
import com.algorand.auction.usecase.RetrieveUserDataUseCase;
import com.algorand.auction.usecase.TransactionHistoryRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.algorand.auction.model.TransactionBuilder.aTransaction;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RetrieveUserDataUseCaseTest {

    private RetrieveUserDataUseCase underTest;
    private UserRepository userRepository = mock(UserRepository.class);
    private TransactionHistoryRepository transactionHistoryRepository = mock(TransactionHistoryRepository.class);

    @BeforeEach
    void setUp() {
        underTest = new RetrieveUserDataUseCase(userRepository, transactionHistoryRepository);
    }

    @Test
    void createTokenForAuthenticatedUser() {
        String username = "AN_USERNAME";
        String password = "A_PASSWORD";
        User user = new User();
        user.setUserName(username);
        Transaction transaction = aTransaction().build();

        when(userRepository.authenticate(username, password)).thenReturn(right(user));
        when(transactionHistoryRepository.retrieveTransactionListFor(user)).thenReturn(right(asList(transaction)));
        final CredentialsRequest credentials = new CredentialsRequest(username, password);
        Either<FailureError, AuthenticationResponse> result = underTest.authenticate(credentials.username, credentials.password);
        assertTrue(result.isRight());
        AuthenticationResponse response = result.get();
        assertThat(response.username, equalTo(user.getUserName()));
        assertThat(response.token, equalTo(underTest.getTokenForUser(username)));
        assertThat(response.transactions, contains(transaction));
    }

    @Test
    void whenRepositoryDoesNotRecognizeUserThenNoToken() {
        when(userRepository.authenticate("AN_USERNAME", "A_PASSWORD")).thenReturn(left(new NoRecordError(0)));
        final CredentialsRequest credentials = new CredentialsRequest("AN_USERNAME", "A_PASSWORD");
        Either<FailureError, AuthenticationResponse> result =  underTest.authenticate(credentials.username, credentials.password);
        assertTrue(result.isLeft());
    }
}