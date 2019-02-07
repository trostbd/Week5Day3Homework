package com.example.lawre.week5day3homework.managers;

import android.support.annotation.NonNull;

import java.util.List;

public class Contact
{
    String name, email;
    List<String> numberList;

    public Contact(String name, List<String> numberList) {
        this.name = name;
        this.numberList = numberList;
    }

    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<String> numberList) {
        this.numberList = numberList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "Contact(" + "name='" + name + '\'' + ", email=" + email + '}';
    }
}