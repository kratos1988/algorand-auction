package com.algorand.auction.rest;

import com.algorand.auction.rest.response.AuthenticationResponse;
import com.algorand.auction.usecase.RetrieveUserDataUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController("user")
@RequestMapping(value = "/api")
public class UserController {

    private final RetrieveUserDataUseCase retrieveUserDataUseCase;

    public UserController(
            RetrieveUserDataUseCase retrieveUserDataUseCase
    ) {
        this.retrieveUserDataUseCase = retrieveUserDataUseCase;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse authenticationResponse = retrieveUserDataUseCase.authenticate(request.getUsername(), request.getPassword());
        if (authenticationResponse == null) {
            return status(401).build();
        }
        return ok(authenticationResponse);
    }

}
