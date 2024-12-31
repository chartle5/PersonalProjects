package com.example.houseapprules;

import javafx.beans.property.*;

import java.util.Date;

public class Account {
    private StringProperty username;
    private StringProperty password;

    private StringProperty accountType;

    public Account(){
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.accountType = new SimpleStringProperty();
    }

    public Account(String username, String password, String accountType){
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.accountType = new SimpleStringProperty(accountType);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    // Getters and Setters for password
    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    // Getters and Setters for accountType
    public String getAccountType() {
        return accountType.get();
    }

    public void setAccountType(String accountType) {
        this.accountType.set(accountType);
    }

    public StringProperty accountTypeProperty() {
        return accountType;
    }
}

