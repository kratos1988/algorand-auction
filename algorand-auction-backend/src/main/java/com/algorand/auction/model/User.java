package com.algorand.auction.model;

import java.util.Objects;

public class User {

    private String userName;
    private String publicKey;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(publicKey, user.publicKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, publicKey);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
