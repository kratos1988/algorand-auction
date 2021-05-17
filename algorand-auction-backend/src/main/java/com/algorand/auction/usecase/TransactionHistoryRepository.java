package com.algorand.auction.usecase;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;
import io.vavr.control.Either;

import java.util.List;

public interface TransactionHistoryRepository {
    Either<FailureError, List<Transaction>> retrieveTransactionListFor(User user);
}
