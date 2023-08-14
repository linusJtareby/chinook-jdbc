package com.chinook.chinookjdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chinook.chinookjdbc.models.Customer;
import com.chinook.chinookjdbc.models.CustomerCountry;
import com.chinook.chinookjdbc.models.CustomerGenre;
import com.chinook.chinookjdbc.models.CustomerSpender;

@Component
public class ChinookDAO {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    // >>> Testing the database-connection
    public void testDataBaseConnectionI() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.print(String.format("\n>>> Connection to database %s\n", conn.getMetaData().getURL()));
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    // >>> Building a string with the data from a Customer-record
    // >>> Returns a string
    public String customerStringBuilder(Customer customer) {
        StringBuilder stringBuilder = new StringBuilder("Id: " + customer.id());
        stringBuilder.append("\n Name: " + customer.firstName());
        stringBuilder.append(" " + customer.lastName());
        stringBuilder.append("\n Country: " + customer.country());
        stringBuilder.append("\n Postal code: " + customer.postalCode());
        stringBuilder.append("\n E-mail: " + customer.email());
        stringBuilder.append("\n Phone number: " + customer.phoneNo());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    // >>>Gets all customers from the customer-table and returns a list with records
    // of them<<<
    public List<Customer> getAllCustomersToList() {
        String sql = "SELECT * FROM customer";
        List<Customer> customerList = new ArrayList<Customer>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(12),
                        resultSet.getString(10));
                customerList.add(customer);
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return customerList;
    }

    // >>>Gets the customer whit the inserted id and returns a record of this
    // customer
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(12),
                        resultSet.getString(10));
                return customer;
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    // >>>Gets all customers who's name contains the word input and returns a list
    // with records of all those customers<<<
    public List<Customer> getCustomerByName(String searchWord) {
        String sql = "SELECT * FROM customer WHERE first_name LIKE ?";

        List<Customer> customerList = new ArrayList<Customer>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + searchWord + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(12),
                        resultSet.getString(10));
                customerList.add(customer);
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return customerList;
    }

    // >>>Inserts a new customer in the customer-table
    // >>>Attributes witch are not defined sets to null
    // >>> ID is auto-generated
    public void insertCustomer(String firstName, String lastName, String country, String postalCode, String email,

            String phoneNr) {
        String sql = "insert into customer (first_name, last_name, country, postal_code, phone, email) values (?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, country);
            statement.setString(4, postalCode);
            statement.setString(5, email);
            statement.setString(6, phoneNr);

            statement.executeUpdate();
            statement.close();

        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    // >>>Updates all attributes in an existing customer
    // >>> The customer to change is defined by the last input (int rowIdToChange)
    public void updateCustomer(String firstName, String lastName, String country, String postalCode, String email,
            String phoneNr, int rowIdToChange) {

        String sql = "update customer set first_name = ?, last_name = ?, country = ?, postal_code = ?, phone = ?, email = ? where customer_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, country);
            statement.setString(4, postalCode);
            statement.setString(5, email);
            statement.setString(6, phoneNr);
            statement.setInt(7, rowIdToChange);

            statement.executeUpdate();

        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public CustomerCountry getMostOccurringCountry() {
        String sql = "select country, count (country) as Occurrence from customer group by country order by Occurrence desc limit 1";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerCountry country = new CustomerCountry(resultSet.getString(1), resultSet.getInt(2));
                return country;
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    public CustomerSpender getHighestSpendingCustomer() {
        String sql = "select first_name, last_name, x.sum from customer join (select customer_id, SUM(total) sum from invoice group by customer_id order by sum desc limit 1) as x on x.customer_id = customer.customer_id";

        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerSpender spender = new CustomerSpender(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
                return spender;
            }
            
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return null;

    }

    public List<CustomerGenre> getPopularGenresFromId(int id) {

        List<CustomerGenre> mostpopularGenres = new ArrayList<CustomerGenre>();

        String sql = "select citgrf.first_name, citgrf.last_name, citgrf.name from "
                .concat("(select citgr.first_name, citgr.last_name, citgr.name, rank() over (order by citgr.sum desc) as rn from ")
                .concat("(select citg.first_name, citg.last_name, name, count(name) as sum ")
                .concat("from genre join ")
                .concat("(select cit.first_name, cit.last_name, cit.invoice_id, cit.track_id, genre_id ")
                .concat("from track join ")
                .concat("(select ci.first_name, ci.last_name, ci.invoice_id, track_id ") 
                .concat("from invoice_line join ")
                .concat("(select first_name, last_name, invoice_id from invoice join customer on customer.customer_id = invoice.customer_id ")
                .concat("where customer.customer_id = ?) as ci on ci.invoice_id = invoice_line.invoice_id) as cit on cit.track_id = track.track_id) ")
                .concat("as citg on citg.genre_id = genre.genre_id group by citg.first_name, citg.last_name, name) as citgr) as citgrf where citgrf.rn = 1");

        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerGenre genres = new CustomerGenre(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                mostpopularGenres.add(genres);
            }
            return mostpopularGenres;
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return null;
    }
}
