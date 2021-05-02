package com.algorand.auction.rest;

import com.algorand.auction.model.User;
import com.algorand.auction.rest.request.CredentialsRequest;
import com.algorand.auction.rest.response.AuthenticationResponse;
import com.algorand.auction.usecase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserAuthenticatorTest {

    private UserAuthenticator underTest;
    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        underTest = new UserAuthenticator(userRepository);
    }

    @Test
    void createTokenForAuthenticatedUser() {
        String username = "AN_USERNAME";
        String password = "A_PASSWORD";
        User user = new User();
        user.setUserName(username);

        when(userRepository.authenticate(username, password)).thenReturn(user);
        final CredentialsRequest credentials = new CredentialsRequest(username, password);
        AuthenticationResponse response = underTest.authenticate(credentials.username, credentials.password);
        assertThat(response.username, equalTo(user.getUserName()));
        assertThat(response.token, equalTo(underTest.getTokenForUser(username)));
    }

    @Test
    void whenRepositoryDoesNotRecognizeUserThenNoToken() {
        when(userRepository.authenticate("AN_USERNAME", "A_PASSWORD")).thenReturn(null);
        final CredentialsRequest credentials = new CredentialsRequest("AN_USERNAME", "A_PASSWORD");
        AuthenticationResponse response =  underTest.authenticate(credentials.username, credentials.password);
        assertThat(response, is(nullValue()));
    }
}