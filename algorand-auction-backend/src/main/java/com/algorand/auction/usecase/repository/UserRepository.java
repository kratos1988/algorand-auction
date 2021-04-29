package com.algorand.auction.usecase.repository;

import com.algorand.auction.model.User;

public interface UserRepository {
    String getPublicKeyFor(String userId);
    User getUserById(int userId);
    int getIdByUsername(String userName);
}
