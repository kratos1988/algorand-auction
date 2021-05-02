package com.algorand.auction.rest;

import com.algorand.auction.model.User;
import com.algorand.auction.rest.response.AuthenticationResponse;
import com.algorand.auction.usecase.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;

public class UserAuthenticator {
    private Map<String, String> userTokenMap = new HashMap();

    private final UserRepository userRepository;

    public UserAuthenticator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getTokenForUser(String username) {
        return userTokenMap.get(username);

    }

    public AuthenticationResponse authenticate(
            String username, String password
    ) {
        User user = userRepository.authenticate(username, password);
        if (user == null)
            return null;
        String token = randomUUID().toString();
        userTokenMap.put(user.getUserName(), token);
        return new AuthenticationResponse(user.getUserName(), token);
    }
}
