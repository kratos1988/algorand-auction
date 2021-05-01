package com.algorand.auction.rest.request;

import java.util.Objects;

public class CredentialsRequest {
    public final String username;
    public final String password;

    public CredentialsRequest(
            String username, String password
    ) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialsRequest that = (CredentialsRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
