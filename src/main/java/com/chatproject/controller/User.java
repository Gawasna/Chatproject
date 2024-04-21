package com.chatproject.controller;
public class User {
    private String username;
    private AccountType accountType;
    public User(String username, AccountType accountType) {
        this.username = username;
        this.accountType = accountType;
    }
    public String getUsername() {
        return username;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public enum AccountType {
        ACCOUNT_LOGIN,
        GUEST_LOGIN
    }
}