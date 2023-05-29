package com.example.mobilproje;

public class Match {
    private String fromEmail, toEmail;

    public Match(String fromEmail, String toEmail) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    public Match() {
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }
}
