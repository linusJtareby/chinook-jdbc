### Chinook database handler

## Description

The Chinook database-handler is a Java-Spring based application providing a set of functions for getting and manipulating data
in the Chinook-database. The application is made based on an assignment given in the Noroff accelerate-course and are a program
that implements a set of different methods that executes SQL-code on mainly the customer-table in the Chinook-database.

The application have 9 different methods:

1. Read all the customers in the database, this should display their: Id, first name, last name, country,
   postal code, phone number and email.
2. Read a specific customer from the database (by Id).
3. Read a specific customer by name.
4. Return a page of customers from the database. This takes in limit and offset as parameters.
5. Add a new customer to the database.
6. Update an existing customer.
7. Return the country with the most customers.
8. Get the customer who is the highest spender (has total in invoice table is the largest).
9. For a given customer, their most popular genre (in the case of a tie, displays both). Most popular in this
   context means the genre that corresponds to the most tracks from invoices associated to that
   customer.

The application implements the repository-pattern having a generic "CRUD"-repo as parent inherits to a more general Customer-repo.
The "CustomerRepositoryImplementation"-class then implements the CustomerRepository-class and overrides all it's methods
specifying what the logic. The application uses Spring-boot JPI and hibernate to connect and manipulate the database.

## Requirements

This application requires you to have a local connection to the Chinook database.
This is achieved with local environment variables, which are:
db_user - your postgres username
db_pw - your user password
chinook_db - your database connection url

These need to be accessible on the system where this application is run.

You will also have to have installed:
Java 20.0 or later
PostgresSQL - `https://www.postgresql.org/download/`
Maven 3.6.3 or later

## Contributing

The project is private and only people invited as collaborators can make changes.

## Authors and acknowledgments

The program is written by Gustaf Hasselgren and Linus Täreby and is part of the Java-fullstack-course in noroffs accelerate-trainee-program.
