package com.algorand.auction.usecase.repository;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.User;
import io.vavr.control.Either;

public interface UserRepository {
    String getPublicKeyFor(String userId);
    Either<FailureError, Integer> getIdByUsername(String userName);
    Either<FailureError,User> authenticate(String username, String password);
    Either<FailureError, User> getUserByPublicKey(String publicKey);
    Either<FailureError, User> getUserByUsername(String seller);
}
