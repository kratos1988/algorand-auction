package com.algorand.auction.rest;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.rest.response.AuthenticationResponse;
import com.algorand.auction.usecase.RetrieveUserDataUseCase;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

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
        Either<FailureError, AuthenticationResponse> response = retrieveUserDataUseCase.authenticate(request.getUsername(), request.getPassword());
        if (response.isLeft()) {
            logger.error("Cannot authenticate user " + request.getUsername() + " for cause: " + response.getLeft().getMessage());
            return status(401).build();
        }
        return ok(response.get());
    }

}
