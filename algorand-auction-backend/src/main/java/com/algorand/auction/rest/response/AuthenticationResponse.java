package com.algorand.auction.rest.response;

public class AuthenticationResponse {
    public final String username;
    public final String token;

    public AuthenticationResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
