package com.algorand.auction.usecase;

public interface UserRepository {
    String getPublicKeyFor(String userId);
}
