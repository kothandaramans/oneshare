package com.example.oneshare.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SharedUsers {

    String fullname;
    String email;
    String note;
    String cureentdatetime;
    String amout;
    String pin;

    public SharedUsers() {
    }

    public SharedUsers(String fullname, String email, String note, String amout) {
        this.fullname = fullname;
        this.email = email;
        this.note = note;
        this.amout = amout;
    }

    public SharedUsers(String fullname, String email, String pin) {
        this.fullname = fullname;
        this.email = email;
        this.pin = pin;
    }

    public String  getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setCureentdatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss", Locale.getDefault());
        this.cureentdatetime = sdf.format(new Date());
    }

    public String getCureentdatetime() {
        return cureentdatetime;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getAmout() {
        return amout;
    }

    public void setAmout(String amout) {
        this.amout = amout;
    }
}
