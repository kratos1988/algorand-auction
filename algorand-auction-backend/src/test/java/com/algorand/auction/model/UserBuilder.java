package com.algorand.auction.model;

public class UserBuilder {
    private String userName;
    private String publicKey;

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder withPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUserName(userName);
        user.setPublicKey(publicKey);
        return user;
    }
}