package com.example.buyer.BUYER.b.SignInSignUp;

public class UserNot {
    private String name, email, password, rPassword;

    public UserNot(String name, String email, String password, String rPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rPassword = rPassword;
    }
    public UserNot() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getrPassword() {
        return rPassword;
    }

    public void setrPassword(String rPassword) {
        this.rPassword = rPassword;
    }
}
