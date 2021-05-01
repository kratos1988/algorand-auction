package com.algorand.auction.rest;

import com.algorand.auction.rest.request.CredentialsRequest;
import com.algorand.auction.rest.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

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

    @GetMapping("/login")
    public ResponseEntity<String> login(
            @PathParam("username") String username,
            @PathParam("password") String password
    ) {
        AuthenticationResponse authenticationResponse = userAuthenticator.authenticate(new CredentialsRequest(username, password));
        if (authenticationResponse == null) {
            return status(401).build();
        }
        return ok(authenticationResponse.token);
    }

}
