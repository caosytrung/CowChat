package com.example.mammam.cowchat.models;

/**
 * Created by Mam  Mam on 12/12/2016.
 */

public class UserChat {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int gender;
    private String description;
    private String linkAvartar;
    private String dateCreate;


    public UserChat(){

    }

    public UserChat(String id, String email, String password,
                    String firstName, String lastName,
                    int gender, String description, String linkAvartar,
                    String dateCreate) {

        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.description = description;
        this.linkAvartar = linkAvartar;
        this.dateCreate = dateCreate;

    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
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

    public String getLinkAvartar() {
        return linkAvartar;
    }

    public void setLinkAvartar(String linkAvartar) {
        this.linkAvartar = linkAvartar;
    }
}
