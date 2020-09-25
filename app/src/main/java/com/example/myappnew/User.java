package com.example.myappnew;

public class User {
     String password;
    String name,email,phone;

    public User(String name, String email,String pass) {
        this.name = name;
        this.email = email;
        this.password=pass;

    }

    public User(String mEmail, String mPassword) {
        this.email=mEmail;
        this.password=mPassword;
    }

    public User() {

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
