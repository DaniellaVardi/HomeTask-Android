package com.example.hometask.Models;

public class User {
    private int id;
    private String avatar;
    private String first_name;
    private String last_name;
    private String email;
    private String date_of_birth;

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    // Updated the method name to getDob()
    public String getDob() {
        return date_of_birth;
    }
}