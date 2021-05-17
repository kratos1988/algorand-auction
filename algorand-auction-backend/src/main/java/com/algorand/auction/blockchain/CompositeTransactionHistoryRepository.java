package com.algorand.auction.blockchain;

import com.algorand.algosdk.crypto.Address;
import com.algorand.auction.blockchain.converter.TransactionResponseToDomainConverter;
import com.algorand.auction.blockchain.error.RetrieveHistoryError;
import com.algorand.auction.blockchain.response.TransactionResponse;
import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;
import com.algorand.auction.usecase.TransactionHistoryRepository;
import com.algorand.auction.usecase.repository.UserRepository;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static io.vavr.control.Either.*;
import static java.util.stream.Collectors.toList;

public class CompositeTransactionHistoryRepository implements TransactionHistoryRepository {
    private final BlockchainTransactionHistoryRepository transactionHistoryRepository;
    private final UserRepository userRepository;
    private final TransactionResponseToDomainConverter converter;

    public CompositeTransactionHistoryRepository(
            BlockchainTransactionHistoryRepository transactionHistoryRepository,
            UserRepository userRepository,
            TransactionResponseToDomainConverter converter) {

        this.transactionHistoryRepository = transactionHistoryRepository;
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public Either<FailureError, List<Transaction>> retrieveTransactionListFor(User user) {
        try {
            return transactionHistoryRepository.retrieveTransactionListFor(new Address(user.getPublicKey()))
                    .flatMap(transactionList ->
                            sequenceRight(transactionList.stream().map(this::convertTransaction).collect(toList()))
                                    .map(transactionsSeq -> transactionsSeq.collect(toList())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return left( new RetrieveHistoryError(e));
        }
    }

    private Either<FailureError, Transaction> convertTransaction(TransactionResponse transaction) {
        return userRepository.getUserByPublicKey(transaction.getPaymentTransaction().getReceiver())
                .flatMap(seller -> userRepository.getUserByPublicKey(transaction.getSender()).map(buyer -> Tuple.of(seller, buyer)))
                .flatMap((Tuple2< User, User > input) ->right(converter.apply(input._1, input._2, transaction.getPaymentTransaction().getAmount())));
    }

}
