package com.chinook.chinookjdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChinookDAO {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public void testDataBaseConnectionI() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.print(String.format("\n>>> Connection to database %s\n", conn.getMetaData().getURL()));
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public void printAllCustomers() {
        String sql = "SELECT * FROM customer";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("\n");
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " "
                        + resultSet.getString(3) + " " + resultSet.getString(8) + " "
                        + resultSet.getString(9) + " " + resultSet.getString(10) + " " + resultSet.getString(12));
                // System.out.println("\n");

            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public void getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("\n");
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " "
                        + resultSet.getString(3) + " " + resultSet.getString(8) + " "
                        + resultSet.getString(9) + " " + resultSet.getString(10) + " " + resultSet.getString(12));

            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }

    }

    public List<Customer> getAllCustomersToList() {
        String sql = "SELECT * FROM customer";
        List<Customer> customerList = new ArrayList<Customer>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(10),
                        resultSet.getString(12));
                customerList.add(customer);
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return customerList;
    }

    public void printCustomerByName(String searchWord) {
        String sql = "SELECT * FROM customer WHERE first_name LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + searchWord + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("\n");
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " "
                        + resultSet.getString(3) + " " + resultSet.getString(8) + " "
                        + resultSet.getString(9) + " " + resultSet.getString(10) + " " + resultSet.getString(12));

            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

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

    public void getMostOccuringCountry() {
        String sql = "select country, count (country) as Occurrence from customer group by country order by Occurrence desc limit 1";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("\n");
                System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));

            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
}
