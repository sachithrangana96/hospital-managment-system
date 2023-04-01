package com.stack.medex.dto;

import com.stack.medex.enums.AccountType;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private AccountType accountType;


    public User() {
    }

    public User(String firstName, String lastName, String email, String password, AccountType accountType) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setAccountType(accountType);
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AccountType getAccountType() {
        return accountType;
    }


}
