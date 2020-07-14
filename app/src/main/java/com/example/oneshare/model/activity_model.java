package com.example.oneshare.model;

public class activity_model {

    String subject;
    String description;
    double amt;

    public activity_model() {
    }

    public activity_model(String subject, String description, double amt) {
        this.subject = subject;
        this.description = description;
        this.amt = amt;
    }

    public activity_model(String subject, String description) {
        this.subject = subject;
        this.description = description;
     }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }
}
