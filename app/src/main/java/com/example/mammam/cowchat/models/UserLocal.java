package com.example.mammam.cowchat.models;

import android.graphics.Bitmap;

/**
 * Created by Mam  Mam on 12/17/2016.
 */

public class UserLocal {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int gender;
    private String description;
    private Bitmap bitMapAvatar;

    public UserLocal(String id, String email, String password,
                     String firstName, String lastName
            , int gender, String description, Bitmap bitMapAvatar) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.description = description;
        this.bitMapAvatar = bitMapAvatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getBitMapAvatar() {
        return bitMapAvatar;
    }

    public void setBitMapAvatar(Bitmap bitMapAvatar) {
        this.bitMapAvatar = bitMapAvatar;
    }
}
