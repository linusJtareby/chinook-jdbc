package com.chinook.chinookjdbc.dao.models;

public record Customer(int id, String firstName, String lastName, String country, String postalCode, String email,
                String phoneNr) {

}
