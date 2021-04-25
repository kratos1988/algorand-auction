package com.algorand.auction.jdbc;

import com.algorand.auction.usecase.UserRepository;

public class JdbcUserRepository implements UserRepository {
    @Override
    public String getPublicKeyFor(String userId) {
        return null;
    }
}
