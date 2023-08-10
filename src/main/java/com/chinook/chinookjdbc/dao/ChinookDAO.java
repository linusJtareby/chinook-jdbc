package com.chinook.chinookjdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public void readSpecificCustomer(int id) {
        String sql = "SELECT * FROM customer WHERE customer_id =" + id;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("\n");
                System.out.println(resultSet.getString(2) + " " + resultSet.getString(3 + resultSet.getString(2) + " "
                        + resultSet.getString(3) + resultSet.getString(2) + " " + resultSet.getString(3)));
                // System.out.println("\n");

            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }

    }
}
