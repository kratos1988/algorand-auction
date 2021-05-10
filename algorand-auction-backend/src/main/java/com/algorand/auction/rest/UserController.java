package com.algorand.auction.rest;

import com.algorand.auction.rest.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController("user")
public class UserController {

    private final UserAuthenticator userAuthenticator;

    public UserController(
            UserAuthenticator userAuthenticator
    ) {
        this.userAuthenticator = userAuthenticator;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse authenticationResponse = userAuthenticator.authenticate(request.getUsername(), request.getPassword());
        if (authenticationResponse == null) {
            return status(401).build();
        }
        return ok(authenticationResponse);
    }

}
