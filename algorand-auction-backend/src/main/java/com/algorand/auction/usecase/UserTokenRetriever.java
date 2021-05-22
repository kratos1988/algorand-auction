package com.algorand.auction.usecase;

import com.algorand.auction.model.FailureError;
import io.vavr.control.Either;

import java.util.HashMap;
import java.util.Map;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.UUID.randomUUID;

public class UserTokenRetriever {
    private final Map<String, String> userTokenMap = new HashMap<>();

    public String generateTokenFor(String userName) {
        String token = userTokenMap.get(userName);
        if (token == null) {
            token = randomUUID().toString();
            userTokenMap.put(userName, token);
        }
        return token;
    }

    public Either<FailureError, String> getUsernameByToken(String token) {
        String username = userTokenMap.get(token);
        if (username == null) {
            return left(new NotExistingUserError(token));
        }
        return right(username);


    }
}
