package com.chinook.chinookjdbc.models;

public record Customer(int id, String firstName, String lastName, String country, String postalCode, String phoneNo,
        String email) {

}
