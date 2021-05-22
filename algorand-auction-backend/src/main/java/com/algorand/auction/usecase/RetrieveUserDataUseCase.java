package com.algorand.auction.usecase;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;
import com.algorand.auction.rest.response.AuthenticationResponse;
import com.algorand.auction.usecase.repository.UserRepository;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;

import java.util.List;

import static io.vavr.control.Either.right;

public class RetrieveUserDataUseCase {

    private final UserRepository userRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final UserTokenRetriever userTokenRetriever;

    public RetrieveUserDataUseCase(
            UserRepository userRepository,
            TransactionHistoryRepository transactionHistoryRepository,
            UserTokenRetriever userTokenRetriever
    ) {
        this.userRepository = userRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.userTokenRetriever = userTokenRetriever;
    }

    public Either<FailureError,AuthenticationResponse> authenticate(
            String username,
            String password
    ) {
        return userRepository.authenticate(username, password)
                .flatMap(user -> transactionHistoryRepository.retrieveTransactionListFor(user).map(transactions -> Tuple.of(user,transactions)))
                .flatMap((Tuple2<User, List<Transaction>> input) -> buildResponse(input._1, input._2));

    }

    private Either<FailureError, AuthenticationResponse> buildResponse(User user, List<Transaction> transactions) {
        return right(new AuthenticationResponse(user.getUserName(), userTokenRetriever.generateTokenFor(user.getUserName()), transactions));
    }
}
