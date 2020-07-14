package com.example.oneshare.model;

public class User {

    String fullname;
    String email;
    String pin;

    public User() {
    }

    public User(String fullname, String email, String pin) {
        this.fullname = fullname;
        this.email = email;
        this.pin = pin;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
