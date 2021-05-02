package com.algorand.auction.model;

public class User {

    private String userName;
    private String publicKey;

    public User(String userName, String publicKey) {
        this.userName = userName;
        this.publicKey = publicKey;
    }

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
}
