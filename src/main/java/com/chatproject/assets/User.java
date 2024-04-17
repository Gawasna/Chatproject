/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.assets;
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
        DATABASE_LOGIN,
        GUEST_LOGIN
    }
}