package com.algorand.auction.usecase;

import com.algorand.auction.model.FailureError;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.UUID.randomUUID;

public class UserTokenRetriever {

    private final Logger logger = LoggerFactory.getLogger(UserTokenRetriever.class);

    private final Map<String, String> userTokenMap = new HashMap<>();

    public String generateTokenFor(String userName) {
        Optional<String> token = userTokenMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(userName))
                .map(Entry::getKey)
                .findFirst();
        return token.orElse(createNewToken(userName));
    }

    private String createNewToken(String userName) {
        String newToken = randomUUID().toString();
        userTokenMap.put(newToken, userName);
        logger.info("Created new token for user: {}", userName);
        return newToken;
    }

    public Either<FailureError, String> getUserNameBy(String token) {
        String username = userTokenMap.get(token);
        if (username == null) {
            logger.error("Error user not present in the map");
            return left(new NotExistingUserError(token));
        }
        return right(username);
    }
}
