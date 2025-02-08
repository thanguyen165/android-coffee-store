package com.thanguyen.coffeestore.data.model;

import androidx.annotation.NonNull;

public class Profile {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;

    public Profile(String fullName, String phoneNumber, String email, String address) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    @NonNull
    @Override
    public String toString() {
        return this.fullName + " " + this.phoneNumber + " " + this.email + " " + this.address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
