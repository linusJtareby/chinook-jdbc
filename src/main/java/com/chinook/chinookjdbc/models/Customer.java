package com.chinook.chinookjdbc.models;

public record Customer(int id, String firstName, String lastName, String country, int postalCode, int phoneNo, String email) {
    
}
