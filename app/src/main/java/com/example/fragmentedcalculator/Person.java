package com.example.fragmentedcalculator;

public class Person {
    private String name;
    private String email;
    private String phone;
    private String city;

    public Person(String email, String phone, String city, String name) {
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.name = name;
    }

    public Person() {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
