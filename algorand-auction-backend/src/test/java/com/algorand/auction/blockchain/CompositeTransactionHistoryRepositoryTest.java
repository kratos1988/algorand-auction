package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;
import com.algorand.auction.blockchain.converter.TransactionResponseToDomainConverter;
import com.algorand.auction.blockchain.response.TransactionResponse;
import com.algorand.auction.jdbc.DatabaseError;
import com.algorand.auction.jdbc.NoRecordError;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;
import com.algorand.auction.usecase.repository.UserRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.algorand.auction.blockchain.TransactionResponseBuilder.aTransactionResponse;
import static com.algorand.auction.blockchain.response.PaymentTransactionResponseBuilder.aPaymentTransaction;
import static com.algorand.auction.model.TransactionBuilder.aTransaction;
import static com.algorand.auction.model.UserBuilder.anUser;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompositeTransactionHistoryRepositoryTest {

    private CompositeTransactionHistoryRepository underTest;
    private BlockchainTransactionHistoryRepository transactionHistoryRepository = mock(BlockchainTransactionHistoryRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private TransactionResponseToDomainConverter converter = new TransactionResponseToDomainConverter();

    @BeforeEach
    void setUp() {
        underTest = new CompositeTransactionHistoryRepository(transactionHistoryRepository, userRepository, converter);
    }

    @Test
    void retrieveTransactionListFor() throws NoSuchAlgorithmException {
        Address input = new Address("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U");

        TransactionResponse response = aTransactionResponse()
                .withSender("F2EJTM2MHOCKJAV7AK3TE2SUPI7TPX3T2J4XSDSH2XKIDALT5FFKM3N7DA")
                .withPaymentTransaction(
                        aPaymentTransaction()
                                .withReceiver("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U")
                                .withAmount(1936)
                                .build())
                .build();

        User seller = anUser().withPublicKey("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U").build();
        User expectedBuyer = anUser().withPublicKey("F2EJTM2MHOCKJAV7AK3TE2SUPI7TPX3T2J4XSDSH2XKIDALT5FFKM3N7DA").build();
        BigDecimal expectedAmount = new BigDecimal("1936");
        Transaction expectedTransaction  = aTransaction()
                .withAmount(expectedAmount)
                .withSeller(seller)
                .withBuyer(expectedBuyer)
                .build();

        when(userRepository.getUserByPublicKey("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U")).thenReturn(right(seller));
        when(userRepository.getUserByPublicKey("F2EJTM2MHOCKJAV7AK3TE2SUPI7TPX3T2J4XSDSH2XKIDALT5FFKM3N7DA")).thenReturn(right(expectedBuyer));
        when(transactionHistoryRepository.retrieveTransactionListFor(input)).thenReturn(right(asList(response)));
        Either<FailureError, List<Transaction>> result = underTest.retrieveTransactionListFor(seller);

        assertTrue(result.isRight());
        List<Transaction> transactions = result.get();
        assertThat(transactions, contains(expectedTransaction));
    }

    @Test
    void multipleError() throws NoSuchAlgorithmException {
        Address input = new Address("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U");
        TransactionResponse response = aTransactionResponse()
                .withSender("F2EJTM2MHOCKJAV7AK3TE2SUPI7TPX3T2J4XSDSH2XKIDALT5FFKM3N7DA")
                .withPaymentTransaction(
                        aPaymentTransaction()
                                .withReceiver("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U")
                                .withAmount(1936)
                                .build())
                .build();
        User seller = anUser().withPublicKey("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U").build();
        when(transactionHistoryRepository.retrieveTransactionListFor(input)).thenReturn(right(asList(response)));
        DatabaseError firstError = new DatabaseError(null);
        NoRecordError secondError = new NoRecordError(1);
        when(userRepository.getUserByPublicKey("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U")).thenReturn(left(firstError));
        when(userRepository.getUserByPublicKey("F2EJTM2MHOCKJAV7AK3TE2SUPI7TPX3T2J4XSDSH2XKIDALT5FFKM3N7DA")).thenReturn(left(secondError));

        Either<FailureError, List<Transaction>> result = underTest.retrieveTransactionListFor(seller);

        assertTrue(result.isLeft());
        assertThat(result.getLeft(), equalTo(firstError));
    }


    @Test
    void errorWhenRetrievingTransactionList() throws NoSuchAlgorithmException {
        Address input = new Address("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U");
        DatabaseError expectedException = new DatabaseError(null);
        User seller = anUser().withPublicKey("SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U").build();
        when(transactionHistoryRepository.retrieveTransactionListFor(input)).thenReturn(left(expectedException));
        Either<FailureError, List<Transaction>> result = underTest.retrieveTransactionListFor(seller);

        assertTrue(result.isLeft());
        ;
        assertThat(result.getLeft(), equalTo(expectedException));
    }
}