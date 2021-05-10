package com.algorand.auction.usecase.repository;

import com.algorand.auction.model.FailureError;
import io.vavr.control.Either;

import java.math.BigDecimal;

public interface TransactionRepository {
    Either<FailureError, String> saveTransaction(String senderPublicKey, String receiverPublicKey, BigDecimal amount, String notes) throws Exception;
}
